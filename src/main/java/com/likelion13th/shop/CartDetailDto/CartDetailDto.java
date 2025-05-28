package com.likelion13th.shop.CartDetailDto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class CartDetailDto {
    private Long itemId;
    private String itemName;
    private int itemPrice;
    private int count;
    private String itemImgPath;
    public CartDetailDto(Long itemId, String itemName, int itemPrice, int count, String itemImgPath) {
        this.itemId= itemId;
        this.itemName= itemName;
        this.itemPrice= itemPrice;
        this.count= count;
        this.itemImgPath= itemImgPath;
    }
}