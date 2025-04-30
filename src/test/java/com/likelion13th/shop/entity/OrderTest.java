package com.likelion13th.shop.entity;

import com.likelion13th.shop.constant.ItemSellStatus;
import com.likelion13th.shop.constant.OrderStatus;
import com.likelion13th.shop.repository.ItemRepository;
import com.likelion13th.shop.repository.MemberRepository;
import com.likelion13th.shop.repository.OrderItemRepository;
import com.likelion13th.shop.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class OrderTest {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    // 영속성 컨텍스트를 사용하기 위해 EntityManager 빈 주입
    @PersistenceContext
    EntityManager em;

    // 상품 생성 메소드 (주석에 맞춰 코드 추가)
    public Item createItem() {
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

    @Test
    @DisplayName("영속성 전이 테스트")
    public void cascadeTest() {

        Order order = new Order();

        for(int i=0; i<3; i++) {
            Item item = this.createItem();
            itemRepository.save(item);

            OrderItem orderItem = new OrderItem();
            // (추가) 생성한 아이템 세팅
            orderItem.setItem(item);

            // (추가) 주문 수량 - 10
            orderItem.setCount(10);
            // (추가) 주문 가격 - 1000
            orderItem.setOrderPrice(1000);

            orderItem.setCreatedBy(LocalDateTime.now());
            orderItem.setModifiedBy(LocalDateTime.now());

            // (추가) 주문 상품에 추가 - add 사용
            order.addOrderItem(orderItem);
        }

        orderRepository.saveAndFlush(order);
        em.clear();

        Order savedOrder = orderRepository.findById(order.getId())// (추가) id로 방금 생성한 Order 엔티티 조회
                .orElseThrow(EntityNotFoundException::new);
        assertEquals(3, savedOrder.getOrderItemList().size());// (추가) 주문 상품 리스트 사이즈와 비교
    }

    public Order createOrder() {
        Order order = new Order();

        for(int i=0; i<3; i++) {
            // (추가) item 생성
            Item item = new Item();
            itemRepository.save(item);

            // orderItem 생성
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setOrderPrice(10000);
            orderItem.setCount(1);
            orderItem.setOrder(order);
            orderItem.setCreatedBy(LocalDateTime.now());
            orderItem.setModifiedBy(LocalDateTime.now());

            // (추가) order에 주문 상품 추가
            order.addOrderItem(orderItem);
        }

        // (추가) 회원 생성
        Member member = new Member();
        member.setName("김이박");
        member.setEmail("kim@leepark.com");
        member.setPassword("test1234");
        member.setAddress("서울특별시 노원구 화랑로");
        member.setCreatedBy(LocalDateTime.now());
        member.setModifiedBy(LocalDateTime.now());
        memberRepository.save(member);

        // (추가) 주문 생성
        order.setMember(member);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.ORDER);
        order.setCreatedBy(LocalDateTime.now());
        order.setModifiedBy(LocalDateTime.now());
        orderRepository.save(order);

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
}
