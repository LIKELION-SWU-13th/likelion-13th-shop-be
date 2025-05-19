package com.likelion13th.shop.service;

import com.likelion13th.shop.dto.*;
import com.likelion13th.shop.entity.Cart;
import com.likelion13th.shop.entity.CartItem;
import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Long addCartItem(String email, List<CartItemDto> cartItemDtos) {
        // 이메일로 회원 조회
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            Member newMember = new Member();
            newMember.setEmail(email);
            member = memberRepository.save(newMember);
        }

        // 회원의 장바구니를 조회
        Cart cart = cartRepository.findByMember(member);
        if (cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        Long savedItemId = null;
        for (CartItemDto cartItemDto : cartItemDtos) {
            Item item = itemRepository.findById(cartItemDto.getItemId())
                    .orElseThrow(() -> new IllegalArgumentException("아이템ID가 없음"));

            // 장바구니에 이미 있는 상품인지 확인
            CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), cartItemDto.getItemId());

            if (savedCartItem != null) {
                savedCartItem.updateCount(item.getStock());
                savedItemId = savedCartItem.getId();

            } else {
                CartItem savedItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
                cartItemRepository.save(savedItem);
                savedItemId = savedItem.getId();
            }
        }

        return savedItemId;
    }

    // 장바구니 조회
    public List<CartDetailDto> getCartList(String email){
        // 장바구니 상품 리스트 생성
        List<CartDetailDto> cartDetailDtoList = new ArrayList<>();

        Member member = memberRepository.findByEmail(email);
        Cart cart = cartRepository.findByMember(member);
        if(cart == null){
            return cartDetailDtoList;
        }

        // 장바구니 id로 상품 리스트 조회한 후 아이템 리스트 가져오기
        cartDetailDtoList = cartRepository.findCartOrderList(cart.getId());
        return cartDetailDtoList;
    }

    // 장바구니 삭제
    public void deleteCartItem(String email, Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);

        if(!cartItem.getCart().getMember().getEmail().equals(email)){
            throw new IllegalArgumentException();
        }

        cartItemRepository.delete(cartItem);
    }

    // 장바구니 수량 변경
    public void updateCartItemCount(String email, CartDetailDto cartDetailDto){
        CartItem cartItem = cartItemRepository.findById(cartDetailDto.getCartItemId())
                .orElseThrow(EntityNotFoundException::new);

        if(!cartItem.getCart().getMember().getEmail().equals(email)){
            throw new IllegalArgumentException();
        }

        cartItem.updateCount(cartDetailDto.getCount());
    }

    // 장바구니 주문 + 주문 상품 삭제
    public Long orderCartItem(String email, List<CartOrderDto> cartOrderDtoList){
        // 이메일로 회원 조회
        Member member = memberRepository.findByEmail(email);
        if(member == null){
            throw new EntityNotFoundException("사용자가 존재하지 않음: " + email);
        }

        // 해당 회원의 장바구니 조회
        Cart cart = cartRepository.findByMember(member);
        if(cart == null){
            throw new EntityNotFoundException("사용자의 장바구니가 존재하지 않음: " + email);
        }

        List<OrderReqDto> orderReqDtoList = new ArrayList<>();

        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId()).orElseThrow(EntityNotFoundException::new);

            OrderReqDto orderReqDto = new OrderReqDto();
            orderReqDto.setItemId(cartItem.getItem().getId());
            orderReqDto.setCount(cartItem.getCount());

            orderReqDtoList.add(orderReqDto);
        }

        Long orderId = orderService.orders(orderReqDtoList, email);
        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId()).orElseThrow(EntityNotFoundException::new);

            cartItemRepository.delete(cartItem);
        }

        return orderId;
    }
}
