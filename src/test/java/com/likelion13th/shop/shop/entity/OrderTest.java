package com.likelion13th.shop.shop.entity;

import com.likelion13th.shop.constant.ItemSellStatus;
import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.entity.Order;
import com.likelion13th.shop.entity.OrderItem;
import com.likelion13th.shop.repository.ItemRepository;
import com.likelion13th.shop.repository.MemberRepository;
import com.likelion13th.shop.repository.OrderItemRepository;
import com.likelion13th.shop.repository.OrderRepository;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
/*
@SpringBootTest
@Transactional
@EnableJpaAuditing
public class OrderTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private MemberRepository memberRepository;


    // 상품 생성 메소드
    public Item createItem() {
        Item item = new Item();

        // 상품명 - "테스트 상품"
        item.setItemName("테스트 상품");

        // 상품 가격 - 10000
        item.setPrice(10000);

        // 상품 상세 설명 - "테스트 상품 상세 설명"
        item.setItemDetail("테스트 상품 상세 설명");

        // 상품 상태 - SELL
        item.setItemSellStatus(ItemSellStatus.SELL);

        // 상품 수량 - 100
        item.setStock(100);

        // 등록 시간 - 현재
        item.setCreatedBy(LocalDateTime.now());

        // 수정 시간 - 현재
        item.setModifiedBy(LocalDateTime.now());

        return item;
    }

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest() {
        Order order = new Order();

        for(int i = 0; i < 3; i++) {
            Item item = this.createItem();
            itemRepository.save(item);

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item); // (추가) 생성한 아이템 세팅
            orderItem.setCount(10); // (추가) 주문 수량 - 10
            orderItem.setPrice(1000); // (추가) 주문 가격 - 1000
            orderItem.setCreatedBy(LocalDateTime.now());
            orderItem.setModifiedBy(LocalDateTime.now());

            // (추가) 주문 상품에 추가 - add 사용
            order.addItemToOrder(orderItem);
        }

        orderRepository.saveAndFlush(order); // order 하나만 저장
        em.clear(); // 영속성 컨텍스트 초기화

        Order savedOrder = orderRepository.findById(order.getId())
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(3, savedOrder.getOrderItemList().size());
    }

    public Order createOrder() {
        Order order = new Order();

        for(int i=0; i<3; i++) {
            // (추가) item 생성
            Item item = createItem();
            itemRepository.save(item);

            // orderItem 생성
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setPrice(10000);
            orderItem.setCount(1);
            orderItem.setOrder(order);
            orderItem.setCreatedBy(LocalDateTime.now());
            orderItem.setModifiedBy(LocalDateTime.now());

            // (추가) order에 주문 상품 추가
            order.addItemToOrder(orderItem);
        }

        // (추가) 회원 생성
        Member member = new Member();
        member.setEmail("test@example.com");
        member.setPassword("1234");
        member.setCreatedBy(LocalDateTime.now());
        member.setModifiedBy(LocalDateTime.now());
        // Member를 저장
        memberRepository.save(member);

        // (추가) 주문 생성
        order.setMember(member);
        order.setOrderDate(LocalDateTime.now());
        order.setCreatedBy(LocalDateTime.now());
        order.setModifiedBy(LocalDateTime.now());
        // Order를 저장
        orderRepository.saveAndFlush(order);

        return order;
    }

    @Test
    @DisplayName("고아객체 제거 테스트")
    public void orphanRemovalTest() {
        Order order = this.createOrder();
        order.getOrderItemList().remove(0);
        em.flush();
    }

    @Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest() {
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItemList().get(0).getId();
        em.flush();
        em.clear();

        //id로 주문 상품 조회
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(EntityNotFoundException::new);

        System.out.println("Order class: " + orderItem.getOrder().getClass());
        System.out.println("====================================================");
        orderItem.getOrder().getOrderDate();
        System.out.println("====================================================");
    }

}*/
