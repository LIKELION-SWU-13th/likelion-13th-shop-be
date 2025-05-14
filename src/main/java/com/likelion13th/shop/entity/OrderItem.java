package com.likelion13th.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.nio.channels.FileLock;
import java.time.LocalDateTime;

@Entity
@Table(name = "orderItem")
@Getter @Setter
public class OrderItem {
    @Id
    @Column(name = "orderItem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private int count;
    @Column(nullable = false)
    private int orderPrice;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    public static OrderItem createOrderItem(Item item, int count) {
        OrderItem orderItem = new OrderItem();

        // 주문할 상품 세팅
        orderItem.setItem(item);

        // 주문 수량 세팅
        orderItem.setCount(count);

        // 상품 가격을 주문 가격으로 세팅
        orderItem.setOrderPrice(item.getPrice());

        // 주문 수량만큼 상품의 재고 수량을 감소
        item.removeStock(count); // 재고 감소가 일어나는 시점
        return orderItem;
    }

    public int getTotalPrice() {
        return orderPrice * count;
    }

    // 주문을 취소할 경우 addStock 메서드를 호출하여 주문 수량만큼 상품의 재고를 증가
    public void cancel() {
        item.addStock(count);
    }
}
