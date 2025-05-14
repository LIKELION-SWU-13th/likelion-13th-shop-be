package com.likelion13th.shop.dto;

import com.likelion13th.shop.constant.ItemSellStatus;
import com.likelion13th.shop.constant.OrderStatus;
import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.entity.Order;
import com.likelion13th.shop.entity.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class OrderItemDto {

    private Long itemId;
    private String itemName;
    private Integer itemPrice;
    private Integer count;
    private int totalPrice;

    private static ModelMapper modelMapper = new ModelMapper();

    // OrderItem -> OrderItemDto 변환 메서드
    public static OrderItemDto of(OrderItem orderItem) {
        OrderItemDto orderItemDto = modelMapper.map(orderItem, OrderItemDto.class);

        // 수동 매핑: itemId와 itemName은 OrderItem 안의 Item 객체에서 추출
        orderItemDto.setItemId(orderItem.getItem().getId());
        orderItemDto.setItemName(orderItem.getItem().getItemName());

        // 총 가격 계산
        orderItemDto.setTotalPrice(orderItem.getOrderPrice() * orderItem.getCount());

        return orderItemDto;
    }
}