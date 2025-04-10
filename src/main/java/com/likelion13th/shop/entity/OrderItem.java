package com.likelion13th.shop.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="orderitem")
public class OrderItem {
    @Id
    @Column(name="orderitem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="orders")
    private Order order;

    @ManyToOne
    @JoinColumn(name="item")
    private Item item;

    private int price;
    private int count;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;
}