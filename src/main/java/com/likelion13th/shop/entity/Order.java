package com.likelion13th.shop.entity;

import aj.org.objectweb.asm.commons.InstructionAdapter;
import com.likelion13th.shop.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
@Table(name="orders")
public class Order {

    @Id
    @Column(name="order_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id", nullable = false)  // 🔥 NOT NULL 설정
    private Member member;

    @Getter
    private LocalDateTime orderDate;
    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    // 양방향 매핑
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItemList = new ArrayList<>();




    // getter 추가
    public List<OrderItem> getOrderItems() {
        return orderItemList;
    }


    // orderItemList에 주문 상품 정보를 담는다.
    public void addOrderItem(OrderItem orderItem){
        //orderItem 객체를 order 객체의 orderItemList에 추가한다.
        orderItemList.add(orderItem);

        orderItem.setOrder(this);

    }

    //주문 생성 메소드 : 회원과 아이템 리스트로 주문 생성하기
    public static Order createOrder(Member member , List<OrderItem> orderItemList){
        Order order = new Order();
        //상품을 주문한 회원의 정보를 세팅
        order.setMember(member);
        //여러개의 주문 상품을 담을 수 있도록  orderItem
        for (OrderItem orderItem : orderItemList){
            order.addOrderItem(orderItem);
        }
        //주문 상태를 ORDER로 세팅
        order.setOrderStatus(OrderStatus.ORDER);
        //현재 시간을 주문 시간으로 세팅한다.
        order.setOrderDate(LocalDateTime.now());
        return order;
    }
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItemList){
            //orderItem에서 메소드 호출
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
    //주문 취소 시(1) 주문상태를 "CANCEL" 상태로 바꿔주고 , 주문 수량을 상품의 재고에 더해준다
    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;
        for(OrderItem orderItem : orderItemList){
            //orderItem메소드 호출
            orderItem.cancel();
        }
    }
}


