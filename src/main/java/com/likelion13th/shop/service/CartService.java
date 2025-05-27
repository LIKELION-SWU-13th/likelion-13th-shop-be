package com.likelion13th.shop.service;

import com.likelion13th.shop.dto.CartDetailDto;
import com.likelion13th.shop.dto.CartItemDto;
import com.likelion13th.shop.dto.CartOrderDto;
import com.likelion13th.shop.dto.OrderReqDto;
import com.likelion13th.shop.entity.Cart;
import com.likelion13th.shop.entity.CartItem;
import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.repository.CartItemRepository;
import com.likelion13th.shop.repository.CartRepository;
import com.likelion13th.shop.repository.ItemRepository;
import com.likelion13th.shop.repository.MemberRepository;
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
    private final OrderService orderService;

    public Long addCartItem(String email, List<CartItemDto> cartItemDtos){
        // 이메일로 회원 조회
        Member member = memberRepository.findByEmail(email);

        if(member == null){
            Member newMember = new Member();
            newMember.setEmail(email);
            member = memberRepository.save(newMember);
        }

        // 회원의 장바구니를 조회
        Cart cart = cartRepository.findByMember(member);

        if(cart == null){
            Cart newCart = Cart.createCart(member);
            cart = cartRepository.save(newCart);
        }

        // 장바구니에 상품 담기
        Long savedItemId = null;
        for(CartItemDto cartItemDto:cartItemDtos){
            Item item = itemRepository.findById(cartItemDto.getItemId())
                    .orElseThrow(() -> new IllegalArgumentException("아이템ID가 없음"));

            // 장바구니에 이미 있는 상품인지 확인
            CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), cartItemDto.getItemId());

            if(savedCartItem != null){
                savedCartItem.addCount(cartItemDto.getCount());
                savedItemId = savedCartItem.getId();
            }else{
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

        cartDetailDtoList = cartItemRepository.findCartOrderList(cart.getId());
        return cartDetailDtoList;
    }

    // 장바구니 아이템 삭제
    public void deleteCartItem(String email, Long cartItemId){
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);

        // 장바구니에 등록된 멤버 email과 인자로 받은 email이 같은지 비교
        if(!cartItem.getCart().getMember().getEmail().equals(email)){
            throw new IllegalArgumentException();
        }
        // cartRepository 호출해서 삭제
        cartItemRepository.delete(cartItem);
    }

    // 장바구니 수량 변경하기
    public void updateCartItemCount(String email, CartDetailDto cartDetailDto){
        CartItem cartItem = cartItemRepository
                .findById(cartDetailDto.getCartItemId())
                .orElseThrow(EntityNotFoundException::new);// cartRepository 호출해 id값에 맞는 상품 찾기

        if(!cartItem.getCart().getMember().getEmail().equals(email)){
            throw new IllegalArgumentException();
        }

        cartItem.updateCount(cartDetailDto.getCount());// 엔티티에서 작성한 메서드로 상품 수량 정보 업데이트
    }

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

            OrderReqDto orderReqDto = new OrderReqDto();// 객체 생성
            orderReqDto.setItemId(cartItem.getItem().getId());// 객체 아이디 설정
            orderReqDto.setCount(cartItem.getCount());
            orderReqDtoList.add(orderReqDto);
        }

        Long orderId = orderService.orders(orderReqDtoList, email); // orderService 호출해 주문한 id 반환
        for(CartOrderDto cartOrderDto : cartOrderDtoList){
            CartItem cartItem = cartItemRepository.findById(cartOrderDto.getCartItemId())
                    .orElseThrow(EntityNotFoundException::new); // 장바구니 아이템 id로 장바구니 아이템 조회
            // 장바구니 아이템 초기화 (삭제)
            cartItemRepository.delete(cartItem);
        }
        return orderId;
    }
}
