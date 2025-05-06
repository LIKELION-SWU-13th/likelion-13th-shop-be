package com.likelion13th.shop.dto;

import com.likelion13th.shop.constant.ItemSellStatus;
import com.likelion13th.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ItemFormDto {
    // 필드
    private Long id;
    @NotNull
    private String itemName;
    @NotNull
    private Integer price;
    @NotNull
    private String itemDetail;
    @NotNull
    private Integer stock;
    @NotNull
    private ItemSellStatus itemSellStatus;

    //get에서만 사용
    private String itemImgPath;

    // modelMapper  객체 생성
    private static ModelMapper modelMapper = new ModelMapper();

    // Dto -> 엔티티 객체 변환을 위한 메서드 : createItem()
    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }

    // 엔티티 -> Dto 객체 변환을 위한 메서드
    public static ItemFormDto of(Item item) {
        return modelMapper.map(item, ItemFormDto.class);
    }
}
