package com.likelion13th.shop.controller;

import com.likelion13th.shop.dto.ItemFormDto;
import com.likelion13th.shop.dto.OrderDto;
import com.likelion13th.shop.dto.OrderItemDto;
import com.likelion13th.shop.dto.OrderReqDto;
import com.likelion13th.shop.entity.OrderItem;
import com.likelion13th.shop.repository.OrderItemRepository;
import com.likelion13th.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderItemRepository orderItemRepository;

    @PostMapping("/new")
    public ResponseEntity<String> createNewOrder(@RequestParam String email, @RequestBody OrderReqDto orderReqDto){
        try{
            Long orderId = orderService.createNewOrder(email, orderReqDto);
            return ResponseEntity.ok("사용자 : "+email + " 주문 ID : " + orderId);
        } catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문실패 : "+e.getMessage());
        }
    }

    //주분내역 전체 조회
    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAllOrders(@RequestParam String email){
        List<OrderDto> orders = orderService.getAllOrderByUserEmail(email);
        return ResponseEntity.ok(orders);
    }

    //주문 내역 상세 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderItemDto> getOrderDetails(@PathVariable Long orderId, @RequestParam String email){
        OrderItemDto orderItemDto = orderService.getOrderDetails(orderId, email);

        //주문 상세 정보를 반환
        return ResponseEntity.ok(orderItemDto);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId, @RequestParam String email){

        try {
            orderService.cancelOrder(orderId, email);
            return ResponseEntity.ok("주문이 취소되었습니다.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문취소 실패 : "+e.getMessage());
        }
    }
}
