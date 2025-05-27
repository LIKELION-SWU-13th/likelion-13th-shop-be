package com.likelion13th.shop.entity;

import com.likelion13th.shop.Exception.OutOfStockException;
import com.likelion13th.shop.constant.ItemSellStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Item {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private int price;
    private int stock;
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    // 상품 이미지 등록
    private String itemImg;
    private String itemImgPath;

    // 상품 차감
    public void removeStock(int stock){
        int restStock = this.stock - stock;
        if(restStock < 0){
            throw new OutOfStockException("샹품의 재고가 부족합니다.");
        }
        this.stock = restStock;
    }

    public void addStock(int stock){
        this.stock += stock;
    }


}

