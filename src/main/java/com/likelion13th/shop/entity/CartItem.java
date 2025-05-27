package com.likelion13th.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    public static CartItem createCartItem(Cart cart,Item item, int count){
        // CartItem 객체 생성
        CartItem cartItem = new CartItem();

        // 생성한 객체에 Cart 객체 설정(set)
        cartItem.setCart(cart);

        // 생성한 객체에 Item 객체 설정(set)
        cartItem.setItem(item);

        // 생성한 객체에 상품 수량 설정(set)
        cartItem.setCount(count);

        // 반환
        return cartItem;
    }

    public void addCount(int count){
        this.count += count;
    }

    public void updateCount(int count){
        this.count = count;
    }
}
