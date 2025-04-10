package com.likelion13th.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDateTime;
import com.likelion13th.shop.constant.OrderStatus;

@Entity
@Table(name = "orders")
@Getter
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member; // 외래키가 될 컬럼 지정

    private LocalDateTime orderDate;
    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}