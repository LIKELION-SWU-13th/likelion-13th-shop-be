package com.likelion13th.shop.dto;

import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.entity.Order;
import com.likelion13th.shop.entity.OrderItem;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Setter @Getter
public class OrderItemDto {
    private Long id;
    private String itemName;
    private Integer itemPrice;
    private Integer count;
    private int price;

    private static ModelMapper modelMapper = new ModelMapper();

    public static OrderItemDto of(OrderItem orderItem){
        OrderItemDto orderItemDto = modelMapper.map(orderItem, OrderItemDto.class);

        // 수동 매핑: itemId와 itemName은 Item 객체 안에 있으므로 따로 세팅
        Item item = orderItem.getItem();
        if (item != null) {
            orderItemDto.setItemName(item.getItemName());
            orderItemDto.setItemPrice(item.getPrice());
        }

        // 총 가격 계산
        orderItemDto.setPrice(orderItemDto.getItemPrice() * orderItem.getCount());

        return orderItemDto;
    }
}
