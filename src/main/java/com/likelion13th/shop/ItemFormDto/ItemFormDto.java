package com.likelion13th.shop.ItemFormDto;

import com.likelion13th.shop.constant.ItemSellStatus;
import com.likelion13th.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
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
    private ItemSellStatus sellStatus;
    
    private static ModelMapper modelMapper=new ModelMapper();

    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }

    public static ItemFormDto of(Item item){
        return modelMapper.map(item, ItemFormDto.class);
    }

    private String itemImg;
    private String itemImgPath;

    public ItemSellStatus getItemSellStatus() {
        return sellStatus;
    }
}