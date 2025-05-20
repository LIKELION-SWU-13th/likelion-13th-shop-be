package com.likelion13th.shop.entity;

import com.likelion13th.shop.Exception.OutOfStockException;
import com.likelion13th.shop.constant.SellStatus;
import com.likelion13th.shop.dto.ItemFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "item")

public class Item {
    private String itemImg;
    private String itemImgPath;
    private int price;


    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int count;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    private String itemDetail;
    private String itemName;


    @Enumerated(EnumType.STRING)
    private SellStatus sellStatus;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    public static Item createItem(ItemFormDto itemFormDto){
        Item item = new Item();
        item.setItemName(itemFormDto.getItemName());
        item.setItemDetail(itemFormDto.getItemDetail());
        item.setSellStatus(itemFormDto.getSellStatus());
        item.setPrice(itemFormDto.getPrice());
        item.setStock(itemFormDto.getStock());
        item.setCreatedBy(LocalDateTime.now());
        item.setModifiedBy(LocalDateTime.now());
        return item;
    }

    @Override
    public String toString() {
        return "Item(id=" + id +
                ", itemName=" + itemName +
                ", price=" + price +
                ", stock=" + stock +
                ", itemDetail=" + itemDetail +
                ", sellStatus=" + sellStatus +
                ")";
    }

    public void removeStock(int stock){
        int restStock = this.stock - stock;
        if(restStock < 0){
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고 수량:"+this.stock + ")");
        }
        this.stock=restStock;
    }

    public void addStock(int stock){
        this.stock += stock;
    }
}
