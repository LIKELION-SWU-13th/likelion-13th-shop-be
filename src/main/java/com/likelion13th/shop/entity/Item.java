package com.likelion13th.shop.entity;

import com.likelion13th.shop.constant.ItemSellStatus;
import com.likelion13th.shop.constant.OrderStatus;
import com.likelion13th.shop.dto.ItemFormDto;
import com.likelion13th.shop.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "item")

public class Item {
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

    @Column(unique = true)
    private String item_detail;
    private String item_name;


    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    public static Item createItem(ItemFormDto itemFormDto){
        Item item = new Item();
        item.setItem_name(itemFormDto.getItem_name());
        return item;
    }
}
