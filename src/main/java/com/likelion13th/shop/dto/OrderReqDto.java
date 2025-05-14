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

@Getter @Setter
public class OrderReqDto {
    private int   count;
    private Long itemId;

}
