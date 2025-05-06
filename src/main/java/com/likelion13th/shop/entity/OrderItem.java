package com.likelion13th.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.nio.channels.FileLock;
import java.time.LocalDateTime;

@Entity
@Table(name = "orderItem")
@Getter @Setter
public class OrderItem {
    @Id
    @Column(name = "orderItem_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private int count;
    @Column(nullable = false)
    private int orderPrice;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;
}
