package com.likelion13th.shop.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="cart")
public class Cart {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;
}