package com.likelion13th.shop.dto;

import com.likelion13th.shop.constant.SellStatus;
import com.likelion13th.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.modelmapper.ModelMapper;

@Getter
@Setter

public class ItemFormDto {
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
    private SellStatus sellStatus;

    private String itemImgPath;

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }
    public static ItemFormDto of(Item item){ return modelMapper.map(item,ItemFormDto.class);}

}
