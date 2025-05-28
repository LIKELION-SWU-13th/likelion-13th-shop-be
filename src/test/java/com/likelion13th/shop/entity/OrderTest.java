package com.likelion13th.shop.entity;

import com.likelion13th.shop.constant.ItemSellStatus;
import com.likelion13th.shop.constant.OrderStatus;
import com.likelion13th.shop.repository.ItemRepository;
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
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application.properties")
public class OrderTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderRepository orderRepository;

    @PersistenceContext
    EntityManager em;

    @Autowired
    private OrderItemRepository orderItemRepository;

    // Item 생성 메서드
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

    // Order 생성 메서드
    public Order createOrder() {
        Item item = createItem();
        itemRepository.save(item);

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.ORDERED);
        order.setCreatedBy(LocalDateTime.now());
        order.setModifiedBy(LocalDateTime.now());

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrder(order);
        order.getOrderItems().add(orderItem);

        return order;
    }

    @Test
    @DisplayName("고아객체 제거 테스트")
    public void orphanRemovalTest() {
        Order order = this.createOrder();
        orderRepository.save(order);  // 🔥 반드시 먼저 저장해야 함

        order.getOrderItems().remove(0); // 하나 삭제

        em.flush();
    }
};

    /*@Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest() {
        Order order = this.createOrder();
        orderRepository.save(order);

        Long orderItemId = order.getOrderItems().get(0).getId();
        em.flush();
        em.clear();

        // id로 주문 상품 조회
        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(EntityNotFoundException::new);

        System.out.println("Order class: " + orderItem.getOrder().getClass());
        System.out.println("====================================================");
        orderItem.getOrder().getOrderDate();
        System.out.println("====================================================");
    }
}*/
