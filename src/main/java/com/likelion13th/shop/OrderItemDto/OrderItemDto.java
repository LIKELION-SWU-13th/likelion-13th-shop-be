package com.likelion13th.shop.OrderItemDto;

import com.likelion13th.shop.ItemFormDto.ItemFormDto;
import com.likelion13th.shop.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class OrderItemDto {
    private Long ItemId;
    private String itemName;
    private Integer itemPrice;
    private Integer count;
    private int totalPrice;

    private static ModelMapper modelMapper = new ModelMapper();

    public static OrderItemDto of(OrderItem orderItem) {
        OrderItemDto orderItemDto = modelMapper.map(orderItem, OrderItemDto.class);

        orderItemDto.setItemId(orderItem.getItem().getId());
        orderItemDto.setItemName(orderItem.getItem().getItemName());
        orderItemDto.setItemPrice(orderItem.getItem().getPrice());

        orderItemDto.setTotalPrice(orderItem.getTotalPrice());

        return orderItemDto;
    }
}