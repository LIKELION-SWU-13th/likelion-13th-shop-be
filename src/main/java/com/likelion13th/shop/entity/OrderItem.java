package com.likelion13th.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import java.time.LocalDateTime;

@Entity
@Table(name="order_item")
@Getter
@Setter
public class OrderItem {
    @Id
    @Column(name = "order_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 실습 8
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    /*
    @ManyToOne // 외래키
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne // 외래키
    @JoinColumn(name = "item_id")
    private Item item;
    */

    private int price;
    private int count;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    public static OrderItem createOrderItem(Item item, int count){
        OrderItem orderItem = new OrderItem();

        orderItem.setItem(item);

        orderItem.setCount(count);

        orderItem.setPrice(item.getPrice());

        item.removeStock(count);
        return orderItem;
    }

    public int getTotalPrice(){
        return price * count;
    }

    // 주문을 취소할 경우 addStock 메서드를 호출하여 주문 수량만큼 상품의 재고를 증가
    public void cancel(){
        this.getItem().addStock(+count);
    }
}