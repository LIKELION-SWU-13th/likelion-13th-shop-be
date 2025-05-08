package com.likelion13th.shop.test.shop.entity;

import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.entity.Order;
import com.likelion13th.shop.entity.OrderItem;
import com.likelion13th.shop.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static com.likelion13th.shop.constant.ItemSellStatus.SALE;

public class OrderTest {

    //@Autowired
    private ItemRepository itemRepository;

    // 상품 생성 메소드 (주석에 맞춰 코드 추가)
    public Item createItem() {
        Item item = new Item();
        item.setItemName("테스트 상품");
        // 상품명 - "테스트 상품"
        item.setPrice(10000);
        // 상품 가격 - 10000
        item.setItemDetail("테스트 상품 상세 설명");
        // 상품 상세 설명 - "테스트 상품 상세 설명"
        item.setItemSellStatus(SALE);
        // 상품 상태 - SELL
        item.setStock(100);
        // 상품 수량 - 100
        item.setCreatedBy(LocalDateTime.now());
        // 등록 시간 - 현재
        item.setModifiedBy(LocalDateTime.now());
        // 수정 시간 - 현재

        return item;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest() {

        Order order = new Order();

        for (int i = 0; i < 3; i++) {
            Item item = this.createItem();
            itemRepository.save(item);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            // (추가) 생성한 아이템 세팅
            orderItem.setPrice(item.getPrice());
            // (추가) 주문 수량 - 10
            orderItem.setCount(item.getStock());
            // (추가) 주문 가격 - 1000
            orderItem.setCreatedBy(LocalDateTime.now());
            orderItem.setModifiedBy(LocalDateTime.now());

            // (추가) 주문 상품에 추가 - add 사용

        }


//        orderRepository.saveAndFlush(order);
//        em.clear();
//
//        Order savedOrder = // (추가) id로 방금 생성한 Order 엔티티 조회
//				    .orElseThrow(EntityNotFoundException::new);
//        assertEquals(3, // (추가) 주문 상품 리스트 사이즈와 비교 );
    }
}