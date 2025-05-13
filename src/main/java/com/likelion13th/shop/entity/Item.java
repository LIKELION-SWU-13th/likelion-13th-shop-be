package com.likelion13th.shop.entity;

import com.likelion13th.shop.Exception.OutOfStockException;
import com.likelion13th.shop.ItemFormDto.ItemFormDto;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.likelion13th.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@Entity
@ToString
@Table(name="item")
public class Item {
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String itemName;
    private int price;
    private int stock;
    private String itemDetail;

    private String itemImg;
    private String itemImgPath;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    public static Item createItem(ItemFormDto itemFormDto){
        Item item = new Item();
        item.setItemName(itemFormDto.getItemName());
        item.setPrice(itemFormDto.getPrice());
        item.setStock(itemFormDto.getStock());
        item.setItemDetail(itemFormDto.getItemDetail());
        item.setItemImg(itemFormDto.getItemImg());
        item.setItemImgPath(itemFormDto.getItemImgPath());
        return item;
    }

    public void removeStock(int stock) {
        int restStock = this.stock - stock;

        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량: " + this.stock + ")");
        }
        this.stock = restStock;
    }

    public void addStock(int stock) {
        this.stock += stock;
    }
}