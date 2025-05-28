package com.likelion13th.shop.dto;

import com.likelion13th.shop.constant.ItemSellStatus;
import com.likelion13th.shop.entity.Cart;
import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.entity.Member;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.modelmapper.ModelMapper;

@Getter
@Setter

public class CartItemDto{
    private Long itemId;
    private int count;
}

