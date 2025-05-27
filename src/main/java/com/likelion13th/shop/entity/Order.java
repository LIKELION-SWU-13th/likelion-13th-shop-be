package com.likelion13th.shop.entity;

import com.likelion13th.shop.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders") // mysql 예약어 order
@Getter @Setter
public class Order {
    @Id
    @Column(name="order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name="member_id")
    private Member member;

    private LocalDateTime orderDate;
    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // 양방향 매핑
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItem> orderItemList = new ArrayList<>();

    public void addItemToOrder(OrderItem orderItem) {
        this.orderItemList.add(orderItem);
        orderItem.setOrder(this); // 양방향 관계 설정
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList){
        Order order = new Order();

        order.setMember(member);
        for(OrderItem orderItem : orderItemList){
            order.addOrderItem(orderItem);
        }

        // 주문 상태를 ORDER로 세팅
        order.setOrderStatus(OrderStatus.ORDER);
        //
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    // orderItemList에 주문 상품 정보를 담는다.
    public void addOrderItem(OrderItem orderItem){
        // orderItem 객체를 order 객체의 orderItemList에 추가한다.
        orderItemList.add(orderItem);

        orderItem.setOrder(this);
    }

    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItemList){
            // orderItem에서 메소드 호출
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;
        for(OrderItem orderItem : orderItemList){
            orderItem.cancel();
        }
    }
}
