package com.likelion13th.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CartItemDto {
    private Long itemId;
    private int count;
}
