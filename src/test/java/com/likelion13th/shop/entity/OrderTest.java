package com.likelion13th.shop.entity;

import com.likelion13th.shop.constant.SellStatus;
import com.likelion13th.shop.repository.ItemRepository;
import com.likelion13th.shop.repository.MemberRepository;
import com.likelion13th.shop.repository.OrderItemRepository;
import com.likelion13th.shop.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityNotFoundException;
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
    MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @PersistenceContext
    private EntityManager em;

    public Order createOrder() {
        Order order = new Order();

        for (int i = 0; i < 3; i++) {
            Item item = new Item();
            item.setItemName("테스트 상품 " + i);
            item.setPrice(10000);
            item.setItemDetail("테스트 상품 상세 설명 " + i);
            item.setSellStatus(SellStatus.SELL);
            item.setStock(100);
            item.setCreatedBy(LocalDateTime.now());
            item.setModifiedBy(LocalDateTime.now());
            itemRepository.save(item);

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setPrice(10000);
            orderItem.setCount(1);
            orderItem.setOrder(order); // 양방향 매핑
            orderItem.setCreatedBy(LocalDateTime.now());
            orderItem.setModifiedBy(LocalDateTime.now());

            order.addOrderItem(orderItem);
        }
        Member member = new Member();
        member.setEmail("test@test.com");
        member.setPassword("1234");
        member.setName("테스트회원");
        member.setCreatedBy("test");
        member.setModifiedBy("test");
        memberRepository.save(member);

        order.setMember(member);
        order.setCreatedBy(LocalDateTime.now());
        order.setModifiedBy(LocalDateTime.now());

        return orderRepository.save(order);
    }

    @Test
    @DisplayName("고아 객체 제거 테스트")
    @Transactional
    @Rollback(false) // delete 쿼리를 보기 위해 롤백 방지
    public void orphanRemovalTest() {
        Order order = createOrder();

        // 첫 번째 OrderItem 제거
        order.getOrderItemList().remove(0);

        em.flush();  // DB 반영
        em.clear();  // 영속성 컨텍스트 초기화
    }


    /*@Test
    @DisplayName("지연 로딩 테스트")
    public void lazyLoadingTest() {
        Order order = this.createOrder();
        Long orderItemId = order.getOrderItemList().get(0).getId();
        em.flush();
        em.clear();

        OrderItem orderItem = orderItemRepository.findById(orderItemId)
                .orElseThrow(EntityNotFoundException::new);

        System.out.println("Order class: " + orderItem.getOrder().getClass());
        System.out.println("====================================================");
        orderItem.getOrder().getOrderDate(); // Lazy 로딩 시점
        System.out.println("====================================================");
    }*/
}
