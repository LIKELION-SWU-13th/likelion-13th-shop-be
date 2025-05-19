package com.likelion13th.shop.entity;

import com.likelion13th.shop.entity.Order;
import com.likelion13th.shop.entity.OrderItem;
import com.likelion13th.shop.repository.ItemRepository;
import com.likelion13th.shop.repository.MemberRepository;
import com.likelion13th.shop.repository.OrderRepository;
import com.likelion13th.shop.repository.OrderItemRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
public class OrderTest {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository OrderItemRepository;
    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    private EntityManager em;

    // 실습 6-7
    //@Test
    @DisplayName("상품 등록 테스트")
    public Item createItem(){
        Item item = new Item();

        item.setItemName("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStock(100);
        item.setCreatedBy(LocalDateTime.now());
        item.setModifiedBy(LocalDateTime.now());

        return item;
    }


    // 실습 6
    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest(){
        Order order = new Order();

        for(int i =0; i < 3; i++){
            Item item = this.createItem();
            itemRepository.save(item);

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setCount(10);
            orderItem.setPrice(1000);
            orderItem.setCreatedBy(LocalDateTime.now());
            orderItem.setModifiedBy(LocalDateTime.now());

            orderItem.setOrder(order);

            // add 사용 추가
            order.getOrderItemList().add(orderItem);
        }

        orderRepository.saveAndFlush(order);
        em.clear();

        Order savedOrder = orderRepository.findById(order.getId())
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(3, savedOrder.getOrderItemList().size());
    }


    // 실습 7
    @DisplayName("주문 생성 테스트")
    public Order createOrder() {
        Order order = new Order();

        for (int i = 0; i < 3; i++) {
            Item item = this.createItem();
            itemRepository.save(item);

            item.setItemName("테스트 상품" + i);
            item.setPrice(10000);
            item.setItemDetail("테스트 상품 상세 설명");
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStock(100);
            item.setCreatedBy(LocalDateTime.now());
            item.setModifiedBy(LocalDateTime.now());

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setPrice(10000);
            orderItem.setCount(1);
            orderItem.setOrder(order);
            orderItem.setCreatedBy(LocalDateTime.now());
            orderItem.setModifiedBy(LocalDateTime.now());

            order.getOrderItemList().add(orderItem);
        }

        // 멤버
        Member member = new Member();
        member.setName("김하나");
        member.setEmail("rainshn@swu.ac.kr");
        member.setPassword("Password1234");
        member.setAddress("서울시 노원구");
        memberRepository.save(member);

        // 주문
        order.setMember(member);
        order.setCreatedBy(LocalDateTime.now());
        order.setModifiedBy(LocalDateTime.now());
        order.setName("테스트 주문");
        order.setPrice(50000);
        order.setItemDetail("테스트 주문 상세 설명");
        order.setStock(50);
        order.setItemSellStatus(ItemSellStatus.SELL);
        order.setStatus(OrderStatus.ORDER);

        return order;
    }

    @Test
    @DisplayName("고아객체 제거 테스트")
    public void orphanRemovalTest() {
        Order order = this.createOrder();
        orderRepository.save(order);
        order.getOrderItemList().remove(0);

        em.flush();
    }

    // 실습 8
    @Test
    @DisplayName("지연 로딩 테스트")
    @Transactional
    public void lazyLoadingTest() {
        Order order = this.createOrder();
        orderRepository.save(order);
        em.flush();
        em.clear();

        Long orderItemId = order.getOrderItemList().get(0).getId();

        //id로 주문 상품 조회
        OrderItem orderItem = OrderItemRepository.findById(orderItemId)
                .orElseThrow(EntityNotFoundException::new);

        System.out.println("Order class: " + orderItem.getOrder().getClass());
        System.out.println("====================================================");
        orderItem.getOrder().getOrderDate(); // getOrderDate()를 출력하는 코드를 추가하면 해당 경고문은 사라질 것
        System.out.println("====================================================");
    }
}