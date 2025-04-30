package com.likelion13th.shop.entity;


import com.likelion13th.shop.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table (name = "orders")
@Getter @Setter
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id") // 외래키 컬럼 지정
    private Member member;

    // 양방향 매핑
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItem> orderItemList = new ArrayList<>();

    private LocalDateTime orderDate;
    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public void addOrderItem(OrderItem orderItem) {
        orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }
}
