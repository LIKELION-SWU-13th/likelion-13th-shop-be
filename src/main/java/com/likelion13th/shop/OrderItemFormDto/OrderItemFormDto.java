package com.likelion13th.shop.OrderItemFormDto;

import com.likelion13th.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemFormDto {
    private Item item;
    private int price;
    private int count;
}
