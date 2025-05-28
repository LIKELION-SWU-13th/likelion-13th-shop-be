package com.likelion13th.shop.CartOrderDto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CartOrderDto{
    private Long itemId;
    private List<CartOrderDto> cartOrderDtoList;
}