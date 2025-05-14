package com.likelion13th.shop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderReqDto {
    private String itemName;
    private Long itemId;
    private Integer count;
}
