package com.likelion13th.shop.controller;

import com.likelion13th.shop.dto.CartDetailDto;
import com.likelion13th.shop.dto.CartItemDto;
import com.likelion13th.shop.dto.CartOrderDto;
import com.likelion13th.shop.service.CartService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<String> addCartItems(@RequestParam("email") String email,
                                          @RequestBody List<CartItemDto> cartItemDtos){
        cartService.addCartItem(email, cartItemDtos);
        return ResponseEntity.ok("장바구니에 상품을 담았음");
    }

    @GetMapping
    public ResponseEntity<List<CartDetailDto>> getCart(@RequestParam("email") String email){
        List<CartDetailDto> cartDetailDto = cartService.getCartList(email);
        return ResponseEntity.ok(cartDetailDto);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<String> deleteCartItem(@RequestParam("email") String email, @PathVariable("cartItemId") Long cartItemId){
        try{
            cartService.deleteCartItem(email, cartItemId); // cartService 호출해 상품 삭제
            return ResponseEntity.ok("장바구니에서 상품 삭제됨 : " + cartItemId);
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 상품 ID 없음: " + cartItemId);
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 사용자의 장바구니에 없는 상품임");
        }
    }

    @PatchMapping("")
    public ResponseEntity<String> updateCartItemCount(@RequestParam("email") String email, @RequestBody CartDetailDto cartDetailDto){
        try{
            cartService.updateCartItemCount(email, cartDetailDto); // cartService 호출해 장바구니 수량 업데이트
            return ResponseEntity.ok("상품 ID: " + cartDetailDto.getCartItemId() + ", 수량: " + cartDetailDto.getCount());
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 상품 ID가 없음");
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 사용자의 장바구니에 없는 상품임");
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<String> orderCartItem(@RequestParam("email") String email, @RequestBody CartOrderDto cartOrderDto) {
        // 장바구니에 담긴 상품들의 리스트 가져오기
        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();
        try {
            Long orderId = cartService.orderCartItem(email, cartOrderDtoList); // cartService 호출해 장바구니 주문 생성
            return ResponseEntity.ok("사용자: " + email + ", 주문ID: " + orderId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 실패: " + e.getMessage());
        }
    }

}
