package com.likelion13th.shop.repository;

import com.likelion13th.shop.constant.SellStatus;
import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.entity.QItem;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.util.List;
@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNameTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemName("테스트 상품 1");
        for (Item item : itemList) {
            System.out.println(itemList.toString());
        }
    }

    public void createItemList() {
        for(int i=1; i<=10; i++){
            Item item = new Item();
            item.setItemName("테스트 상품 "+i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명 "+i);
            item.setSellStatus(SellStatus.SELL);
            item.setStock(100);
            item.setCreatedBy(LocalDateTime.now());
            item.setModifiedBy(LocalDateTime.now());

            itemRepository.save(item);
        }
    }



    @Test
    @DisplayName("가격 내림차순 조회 테스트")
    public void findByPriceLessThanOrderByPriceDescTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
        System.out.println(itemList);
    }

    @Test
    @DisplayName("@Query를 이용한 상품 조회 테스트")
    public void findByItemDetailTest() {
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세 설명");
        for (Item item : itemList) {
            System.out.println(item);
        }
    }

    @Test
    @DisplayName("Querydsl 조회 테스트")
    public void queryDslTest() {
        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;

        List<Item> itemList = queryFactory
                .selectFrom(qItem)
                .where(
                        qItem.sellStatus.eq(SellStatus.SELL),
                        qItem.itemDetail.like("%테스트 상품 상세 설명%")
                )
                .orderBy(qItem.price.desc())
                .limit(1)
                .fetch();

        for(Item item : itemList) {
            System.out.println(item.toString());
        }
    }

}
