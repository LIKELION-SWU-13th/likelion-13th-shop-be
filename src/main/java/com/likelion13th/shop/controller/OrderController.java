package com.likelion13th.shop.controller;

import com.likelion13th.shop.dto.OrderReqDto;
import com.likelion13th.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/new")
    public ResponseEntity<String> createNewOrder(@RequestBody OrderReqDto orderReqDto, @RequestParam("email") String email){
        try{
            Long orderId = orderService.createNewOrder(orderReqDto, email);

            return ResponseEntity.ok("사용자 : " + email + ", 주문 ID : " + orderId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 실패 : " + e.getMessage());
        }
    }
}
