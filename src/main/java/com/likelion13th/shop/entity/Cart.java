package com.likelion13th.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.time.LocalDateTime;

@Entity
@Table(name="cart")
@Getter
@Setter
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    public static Cart createCart(Member member){
        // Cart 객체 생성
        Cart cart = new Cart();

        // 생성한 cart 객체에 member 설정(set메서드 사용)
        cart.setMember(member);

        // cart 객체 반환
        return cart;
    }

}
