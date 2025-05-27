package com.likelion13th.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "order_item") // 테이블 이름 지정
public class OrderItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int price;
    private int count;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();

        // 주문할 상품 세팅
        orderItem.setItem(item);

        // 주문 수량 세팅
        orderItem.setCount(count);

        // 상품 가격을 주문 가격으로 세팅
        orderItem.setPrice(item.getPrice());

        // 주문 수량 만큼 상품의 재고 수량을 감소
        item.removeStock(count);
        return orderItem;
    }

    // 주문 가격과 주문 수량을 곱해서 주문 총 가격을 계산
    public int getTotalPrice(){
        return price * count;
    }

    // 주문 취소 시 해당 상품의 재고를 복구
    public void cancel(){
        item.addStock(count);
    }
}
