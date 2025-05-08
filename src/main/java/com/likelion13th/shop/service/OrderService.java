package com.likelion13th.shop.service;

import com.likelion13th.shop.dto.OrderDto;
import com.likelion13th.shop.dto.OrderItemDto;
import com.likelion13th.shop.dto.OrderReqDto;
import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.entity.Order;
import com.likelion13th.shop.entity.OrderItem;
import com.likelion13th.shop.repository.ItemRepository;
import com.likelion13th.shop.repository.MemberRepository;
import com.likelion13th.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    public Long createNewOrder(OrderReqDto orderReqDto, String email) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            member = new Member();
            member.setEmail(email);
            memberRepository.save(member);
        }
        Long itemId = orderReqDto.getItemId();
        Item item = itemRepository.findById(itemId).orElseThrow(()->new IllegalAccessError("상품이 존재하지 않습니다. "));

        OrderItem orderItem = OrderItem.createOrderItem(item, orderReqDto.getCount());

        Order order = Order.createOrder(member, Collections.singletonList(orderItem));

        orderRepository.save(order);

        return order.getId();
    }

}
