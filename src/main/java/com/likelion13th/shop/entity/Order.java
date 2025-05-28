package com.likelion13th.shop.entity;

import com.likelion13th.shop.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 실습 6
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItemList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;
    private LocalDateTime orderDate;

    private String name;
    private int price;
    private String itemDetail;
    private int stock;

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public void addOrderItem(OrderItem orderItem){
        orderItemList.add(orderItem);

        orderItem.setOrder(this);
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList){
        Order order = new Order();

        order.setMember(member);

        for(OrderItem orderItem : orderItemList){
            order.addOrderItem(orderItem);
        }

        order.setOrderStatus(OrderStatus.ORDER);

        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItemList){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;
        for(OrderItem orderItem : orderItemList){}{
            // orderItem에서 메소드 호출
            OrderItem cancel;
        }
    }
}
