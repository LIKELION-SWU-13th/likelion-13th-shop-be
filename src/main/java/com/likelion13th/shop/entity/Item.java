package com.likelion13th.shop.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.likelion13th.shop.constant.ItemSellStatus;

@Entity
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

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;
}


