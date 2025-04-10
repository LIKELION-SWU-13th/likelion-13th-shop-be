package com.likelion13th.shop.entity;

import com.likelion13th.shop.constant.ItemSellStatus;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name="item")
@Getter
public class Item {
    @Id
    @Column(name="item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;

    private int price;
    private int stock;
    private String itemDetail;
    private ItemSellStatus itemSellStatus;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;
}
