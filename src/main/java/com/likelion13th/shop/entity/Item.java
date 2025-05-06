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
}
