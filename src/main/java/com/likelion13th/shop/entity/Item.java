package com.likelion13th.shop.entity;

import com.likelion13th.shop.constant.ItemSellStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Entity
@Table(name = "item")
@Getter @Setter
public class Item {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;

    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private int stock;
    private String itemDetail;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    private String itemImg;
    private String itemImgPath;

    public void removeStock(int stock) {
        int restStock = this.stock - stock;
        if(restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량:"+this.stock+")");
        }
        this.stock = restStock;
    }

    public void addStock(int stock) {
        this.stock += stock;
    }
}
