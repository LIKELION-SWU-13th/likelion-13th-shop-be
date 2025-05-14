package com.likelion13th.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="cart")
@Getter
@Setter
public class Cart {
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 외래키 설정
    @JoinColumn(name = "memeber_id")
    private Member member; // Member 매핑

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;
}
