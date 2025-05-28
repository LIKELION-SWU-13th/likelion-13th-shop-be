package com.likelion13th.shop.entity;
import com.likelion13th.shop.Exception.OutOfStockException;
import com.likelion13th.shop.constant.ItemSellStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    // 정적 팩토리 메서드: 회원 정보를 받아 Cart 생성
    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        cart.setMember(member);
        return cart;
    }
}
