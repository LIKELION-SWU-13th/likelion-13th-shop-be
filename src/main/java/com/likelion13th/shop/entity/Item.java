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
    private itemSellStatus status;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;
}