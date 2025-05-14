package com.likelion13th.shop.service;

import com.likelion13th.shop.dto.OrderDto;
import com.likelion13th.shop.dto.OrderItemDto;
import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.dto.OrderReqDto;
import com.likelion13th.shop.entity.Order;
import com.likelion13th.shop.entity.OrderItem;
import com.likelion13th.shop.repository.ItemRepository;
import com.likelion13th.shop.repository.MemberRepository;
import com.likelion13th.shop.repository.OrderItemRepository;
import com.likelion13th.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    public Long createNewOrder(String email, OrderReqDto orderReqDto){
        Member member = memberRepository.findByEmail(email);
        if(member == null){
            member = new Member();
            member.setEmail(email);
            memberRepository.save(member);
        }

        Long itemId = orderReqDto.getItemId();
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
        OrderItem orderItem = OrderItem.createOrderItem(item, orderReqDto.getCount());
        Order order = Order.createOrder(member, Collections.singletonList(orderItem));
        orderRepository.save(order);
        return order.getId();


    }

    public List<OrderDto> getAllOrderByUserEmail (String email){
        List<Order> orders = orderRepository.findByMemberEmail(email);
        List<OrderDto> orderDtos = new ArrayList<>();
        for(Order order:orders){orderDtos.add(OrderDto.of(order)); }
        return orderDtos;
    }

    public OrderItemDto getOrderDetails(Long orderId, String email){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문 ID 없음 : " + orderId));

        //주문을 생성한 사용자인지
        if (!order.getMember().getEmail().equals(email)){
            throw new IllegalArgumentException("주문자만 접근 가능함");
        }

        //주문에 속한 주문 상품들을 가져온다
        List<OrderItem> orderItems = order.getOrderItemList();

        if(!orderItems.isEmpty()){
            OrderItem orderItem = orderItems.get(0);
            return OrderItemDto.of(orderItem);
        } else {
            throw new IllegalArgumentException("주문 아이템이 없음");
        }
    }

    public void cancelOrder(Long orderId, String email){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new IllegalArgumentException("주문 ID 없음 : " + orderId));


        if(!order.getMember().getEmail().equals(email)){
            throw new IllegalArgumentException("취소 권한이 없음");
        }

        order.cancelOrder(); //주문 상태를 CANCEL로 변경
        orderRepository.save(order);

    }
}
