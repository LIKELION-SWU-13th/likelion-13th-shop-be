package com.likelion13th.shop.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="cartitem") // 객체와 테이블 매핑
public class CartItem {
    @Id
    @Column(name="cartitem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name="item")
    private Item item;

    private int count;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;
}
