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

    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();

        // 상품을 주문한 회원의 정보를 세팅
        order.setMember(member);

        // 여러 개의 주문 상품을 담을 수 있또록 orderItem
        for (OrderItem orderItem : orderItemList) {
            order.addOrderItem(orderItem);
        }

        // 주문 상태를 ORDER로 세팅
        order.setOrderStatus(OrderStatus.ORDER);

        // 현재 시간을 주문 시간으로 세팅
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    // 총 주문 금액을 구하는 메소드
    public int getTotalPrice() {
        int totalPrice = 0;
        for (OrderItem orderItem : orderItemList){
            // orderItem에서 메소드 호출
            totalPrice += orderItem.getTotalPrice();
        }

        return totalPrice;
    }

    // 주문 취소 시 (1) 주문 상태를 "CANCLE" 상태로 바꿔주고, 주문 수량을 상품의 재고에 더해주는 로직
    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCLE;
        for (OrderItem orderItem : orderItemList) {
            // orderItem에서 메서드 호출
            orderItem.cancel();
        }
    }
}
