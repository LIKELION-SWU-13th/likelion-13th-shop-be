package com.likelion13th.shop.entity;

import com.likelion13th.shop.OrderItemFormDto.OrderItemFormDto;
import com.likelion13th.shop.entity.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name="orderitem")
@Getter @Setter
public class OrderItem {
    @Id
    @Column(name="orderitem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*@ManyToOne
    @JoinColumn(name="orders")
    private Order order;

    @ManyToOne
    @JoinColumn(name="item")
    private Item item;*/

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

    public static OrderItem createOrderItem(OrderItemFormDto orderItemFormDto, Item item, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(orderItemFormDto.getPrice());
        orderItem.setCount(orderItemFormDto.getCount());
        orderItem.setItem(item);
        orderItem.setOrder(order);
        orderItem.setCreatedBy(LocalDateTime.now());
        orderItem.setModifiedBy(LocalDateTime.now());

        return orderItem;
    }
}