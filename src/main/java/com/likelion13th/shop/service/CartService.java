package com.likelion13th.shop.service;

import com.likelion13th.shop.CartDetailDto.CartDetailDto;
import com.likelion13th.shop.CartItemDto.CartItemDto;
import com.likelion13th.shop.CartOrderDto.CartOrderDto;
import com.likelion13th.shop.OrderReqDto.OrderReqDto;
import com.likelion13th.shop.entity.Cart;
import com.likelion13th.shop.entity.CartItem;
import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    public Long addCartItems(String email, List<CartItemDto> cartItemDtos){
        Member member = memberRepository.findByEmail(email);

        if (member == null){
            Member newMember = new Member();
            newMember.setEmail(email);
            member = memberRepository.save(newMember);
        }

        Cart cart = cartRepository.findByMember(member);
        if(cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        Long savedItemId = null;
        for(CartItemDto cartItemDto : cartItemDtos){
            Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(()->new IllegalArgumentException("아이템ID가 없음"));

            CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());
            if (savedCartItem != null){
                savedCartItem.updateCount(cartItemDto.getCount());
                savedItemId = savedCartItem.getId();
            } else {
                CartItem savedItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
                cartItemRepository.save(savedItem);
                savedItemId = savedItem.getId();
            }
        }

        return savedItemId;
    }

    public List<CartDetailDto> getCartList(String email){
        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMember(member);
        if(cart == null){
            return cartDetailDtoList;
        }

        cartDetailDtoList = cartRepository.findCartOrderList(cart.getId());
        return cartDetailDtoList;
    }

    public void deleteCartItem(String email, Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);

        if (!cartItem.getCart().getMember().getEmail().equals(email)) {
            throw new IllegalArgumentException();
        }

        cartItemRepository.delete(cartItem);
    }

    public void updateCartItemCount(String email, CartDetailDto cartDetailDto){
        CartItem cartItem = cartItemRepository.findById(cartDetailDto.getItemId()).orElseThrow(EntityNotFoundException::new);

        if (!cartItem.getCart().getMember().getEmail().equals(email)) {
            throw new IllegalArgumentException();
        }

        cartItem.setCount(cartDetailDto.getCount());
    }

    public Long orderCartItem(String email, List<CartOrderDto> cartOrderDtoList) {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new EntityNotFoundException("사용자가 존재하지 않음 : " + email);
        }

        Cart cart = cartRepository.findByMember(member);
        if(cart == null){
            throw new EntityNotFoundException("사용자의 장바구니가 존재하지 않음 : " + email);
        }

        List<OrderReqDto> orderReqDtoList = new ArrayList<>();

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getItemId()).orElseThrow(EntityNotFoundException::new);

            OrderReqDto orderReqDto = new OrderReqDto();
            orderReqDto.setItemId(cartItem.getId());
            orderReqDto.setCount(cartItem.getCount());
            orderReqDtoList.add(orderReqDto);
        }

        Long orderId = orderService.orders(orderReqDtoList, email);

        for (CartOrderDto cartOrderDto : cartOrderDtoList) {
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
            cartItemRepository.delete(cartItem);
        }

        return orderId;
    }
}