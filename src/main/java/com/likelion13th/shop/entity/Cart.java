package com.likelion13th.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cart")
@Getter @Setter
public class Cart {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    public static Cart createCart(Member member) {
        Cart cart = new Cart();

        cart.setMember(member);

        return cart;
    }
}
