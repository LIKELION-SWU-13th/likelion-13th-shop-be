package com.likelion13th.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="item")
@Getter
@Setter
public class Item {
    @Id
    @Column(name = "item_id")
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

    // 실습 2
    @Override
    public String toString() {
        return "Item(id=" + id + ", itemName=" + itemName + ", price=" + price + ", stock=" + stock + ", itemDetail=" + itemDetail + ")";
    }

    public void removeStock(int stock){
        int restStock = this.stock - stock;
        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고 수가 부족합니다. (현재 재고 수량: " + this.stock + ")");
        }
        this.stock = restStock;
        }

    public void addStock(int stock){
        this.stock += stock;
    }

}
