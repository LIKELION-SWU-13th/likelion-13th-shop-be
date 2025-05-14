package com.likelion13th.shop.controller;

import com.likelion13th.shop.dto.OrderDto;
import com.likelion13th.shop.dto.OrderItemDto;
import com.likelion13th.shop.dto.OrderReqDto;
import com.likelion13th.shop.entity.Order;
import com.likelion13th.shop.repository.OrderRepository;
import com.likelion13th.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @PostMapping("/new")
    public ResponseEntity<String> createNewOrder(@RequestBody OrderReqDto orderReqDto, @RequestParam("email") String email){
        try{
            Long orderId = orderService.createNewOrder(orderReqDto, email);

            return ResponseEntity.ok("사용자 : " + email + ", 주문 ID : " + orderId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 실패 : " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getOrdersbyUserEmail(@RequestParam("email") String email){
        List<OrderDto> orders = orderService.getAllOrderByUserEmail(email);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderItemDto> getOrderDetails (@PathVariable("orderId") Long orderId, @RequestParam("email") String email){

        OrderItemDto orderItemDto = orderService.getOrderDetails(orderId, email);

        return ResponseEntity.ok(orderItemDto);
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable("orderId") Long orderId, @RequestParam("email") String email){
        try{
            orderService.cancelOrder(orderId, email);
            return ResponseEntity.ok().body("주문이 취소되었습니다. ");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문취소 실패 : " + e.getMessage());
        }
    }
}
