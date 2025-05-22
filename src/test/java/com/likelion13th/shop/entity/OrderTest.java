package com.likelion13th.shop.entity;

import com.likelion13th.shop.constant.Role;
import com.likelion13th.shop.constant.OrderStatus;
import com.likelion13th.shop.repository.ItemRepository;
import com.likelion13th.shop.repository.MemberRepository;
import com.likelion13th.shop.repository.OrderItemRepository;
import com.likelion13th.shop.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import com.likelion13th.shop.constant.ItemSellStatus;
import java.time.LocalDateTime;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
public class OrderTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Member INSERT 쿼리 확인")
    public void testMemberInsertQuery() {
        Member member = new Member();
        member.setName("우예빈");
        member.setEmail("yb2527@swu.ac.kr");
        member.setPassword("password");
        member.setAddress("서울특별시 노원구");
        member.setRole(Role.USER);
        member.setCreatedBy(LocalDateTime.now());
        member.setModifiedBy(LocalDateTime.now());
        em.persist(member);
        em.flush();
    }

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

    public Order createOrder() {
        Order order = new Order();

        for(int i=0; i<1; i++) {
            Item item = this.createItem();
            em.persist(item);

            // orderItem 생성
            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setPrice(10000 + (i * 1000));
            orderItem.setCount(1);
            orderItem.setOrder(order);
            orderItem.setCreatedBy(LocalDateTime.now());
            orderItem.setModifiedBy(LocalDateTime.now());
            em.persist(orderItem);
            order.getOrderItemList().add(orderItem);
        }

        Member member = new Member();
        member.setName("우예빈");
        member.setEmail("yb2527@swu.ac.kr");
        member.setPassword("password");
        member.setAddress("서울특별시 노원구");
        member.setRole(Role.USER);
        member.setCreatedBy(LocalDateTime.now());
        member.setModifiedBy(LocalDateTime.now());
        em.persist(member);

        order.setMember(member);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.ORDERED);

        return order;
    }
}