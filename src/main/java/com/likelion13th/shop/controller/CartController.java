package com.likelion13th.shop.controller;

import com.likelion13th.shop.dto.CartDetailDto;
import com.likelion13th.shop.dto.CartItemDto;
import com.likelion13th.shop.dto.CartOrderDto;
import com.likelion13th.shop.service.CartService;
import com.likelion13th.shop.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final OrderService orderService;

    // 장바구니 담기
    @PostMapping("/add")
    public ResponseEntity<String> addCartItems(@RequestParam(name = "email") String email,
                                               @RequestBody List<CartItemDto> cartItemDtos){

        cartService.addCartItem(email, cartItemDtos);

        return ResponseEntity.ok("장바구니에 상품을 담았음");
    }

    // 장바구니 조회
    @GetMapping
    public ResponseEntity<List<CartDetailDto>> getCart(@RequestParam(name = "email") String email){

        // carService 호출해서 장바구니 목록 조회
        List<CartDetailDto> cartDetailDto = cartService.getCartList(email);

        return ResponseEntity.ok(cartDetailDto);
    }

    // 장바구니 삭제
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<String> deleteCartItem(@RequestParam(name = "email") String email, @PathVariable("cartItemId") Long cartItemId){
        try{
            cartService.deleteCartItem(email, cartItemId);
            return ResponseEntity.ok("장바구니에서 상품 삭제됨 : " + cartItemId);
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 상품 ID 없음 : " + cartItemId);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 사용자의 장바구니에 없는 상품임");
        }
    }

    // 장바구니 수량 변경
    @PatchMapping("")
    public ResponseEntity<String> updateCartItemCount(@RequestParam(name = "email") String email, @RequestBody CartDetailDto cartDetailDto){
        try{
            cartService.updateCartItemCount(email, cartDetailDto);
            return ResponseEntity.ok("상품 ID: " + cartDetailDto.getCartItemId() + ", 수량: " + cartDetailDto.getCount());
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 상품 ID가 없음");
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("해당 사용자의 장바구니에 없는 상품임");
        }
    }

    // 장바구니 주문
    @PostMapping("/orders")
    public ResponseEntity<String> orderCartItem(@RequestParam(name = "email") String email, @RequestBody CartOrderDto cartOrderDto){
        // 장바구니에 담긴 상품들의 리스트 가져오기
        List<CartOrderDto> cartOrderDtoList = cartOrderDto.getCartOrderDtoList();
        try{
            Long orderId = cartService.orderCartItem(email, cartOrderDtoList);
            return ResponseEntity.ok("사용자: " + email + ", 주문ID: " + orderId);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 실패: " + e.getMessage());
        }
    }
}
