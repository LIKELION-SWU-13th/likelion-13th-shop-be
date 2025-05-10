package com.likelion13th.shop.dto;

import com.likelion13th.shop.entity.OrderItem;
import org.modelmapper.ModelMapper;

public class OrderItemDto {
    private Long itemId;
    private String itemName;
    private Integer itemPrice;
    private Integer count;
    private int totalPrice;

    private static ModelMapper modelMapper = new ModelMapper();

    public static OrderItemDto of(OrderItem orderItem){
        OrderItemDto orderItemDto = modelMapper.map(orderItem, OrderItemDto.class);

        orderItemDto.itemId = orderItem.getItem().getId();

        orderItemDto.itemName = orderItem.getItem().getItemName();

        orderItemDto.totalPrice = orderItem.getTotalPrice();

        return orderItemDto;
    }
}
