package com.likelion13th.shop.dto;

import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class OrderReqDto {
    private Long itemId;
    private Integer count;
}
