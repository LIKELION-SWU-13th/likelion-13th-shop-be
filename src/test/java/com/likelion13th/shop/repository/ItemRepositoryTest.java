package com.likelion13th.shop.repository;

import com.likelion13th.shop.constant.ItemSellStatus;
import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.entity.QItem;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@Transactional
@Commit
@TestPropertySource(locations = "classpath:application.properties")
class ItemRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest() {
        Item item = new Item();
        item.setItemName("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStock(100);
        item.setCreatedBy(LocalDateTime.now());
        item.setModifiedBy(LocalDateTime.now());

        Item savedItem = itemRepository.save(item);
        System.out.println(savedItem.toString());
    }

    public void createItemList() {
        for (int i = 1; i <= 10; i++) { // (추가) item 10개 생성
            Item item = new Item();
            item.setItemName("테스트 상품 " + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명 " + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStock(100);
            item.setCreatedBy(LocalDateTime.now());
            item.setModifiedBy(LocalDateTime.now());

            itemRepository.save(item); // (추가) item 저장
        }
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNameTest() {
        this.createItemList(); // 상품 목록 생성
        List<Item> itemList = itemRepository.findByItemName("테스트 상품 1");
        for (Item item : itemList) { // (추가) itemList 출력 코드
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDescTest() {
        this.createItemList();

        // (추가) 10005원 이하의 상품 조회 후 가격 내림차순 정렬 결과 출력
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        System.out.println("조회된 상품 수: " + itemList.size());
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest() {
        // (추가) "테스트 상품 상세 설명" 조회 후 가격 내림차순 정렬
        this.createItemList(); // 테스트용 상품 10개 생성

        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }

    // 영속성 컨텍스트를 사용하기 위해 EntityManager 빈 주입
    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Querydsl 조회 테스트")
    public void queryDslTest() {
        this.createItemList(); // 테스트용 상품 10개 생성
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;

        // 한 개만 조회되도록 limit(1) 추가
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%테스트 상품 상세 설명%"))
                .orderBy(qItem.price.desc())
                .limit(1);

        List<Item> itemList = query.fetch(); // 실행 시점에 쿼리 날림

        for (Item item : itemList) {
            System.out.println(item.toString());
        }
    }
}
