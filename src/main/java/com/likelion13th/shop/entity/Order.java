package com.likelion13th.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "memeber_id")
    private Member member;

    private LocalDateTime orderDate;
    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
