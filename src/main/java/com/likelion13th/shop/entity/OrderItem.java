package com.likelion13th.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name="orderItem")
@Getter
public class OrderItem {
    @Id
    @Column(name = "orderItem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int price;
    private int count;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;
}
