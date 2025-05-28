package com.likelion13th.shop.controller;

import com.likelion13th.shop.dto.ItemFormDto;
import com.likelion13th.shop.dto.OrderDto;
import com.likelion13th.shop.dto.OrderItemDto;
import com.likelion13th.shop.dto.OrderReqDto;
import com.likelion13th.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/orders")
public class OrderController {
    private final OrderService orderService;

    // 주문 생성하기
    @PostMapping("/new")
    public ResponseEntity<String> createNewOrder (@RequestParam("email") String email, @RequestBody OrderReqDto orderReqDto){
        try{
            // dto와 email로 주문을 생성하는 메소드
            Long orderId = orderService.createNewOrder(orderReqDto, email);

            // 성공적으로 생성될 경우 사용자의 이메일과 주문 id 반환
            return ResponseEntity.ok("사용자: " + email + "주문ID: " + orderId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문실패: "+ e.getMessage());
        }
    }

    // 주문 내역 전체 조회
    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getOrdersByEmail(@RequestParam("email") String email) {

        // 사용자 이메일을 파라미터로 받아와서 해당 사용자 모든 주문을 조회
        List<OrderDto> orders = orderService.getAllOrderByUserEmail(email);
        return ResponseEntity.ok(orders);
    }

    // 주문 내역 상세 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderItemDto> getOrderDetails(@PathVariable("orderId") Long orderId, @RequestParam("email") String email){
        // orderId, Email로 특정 주문의 상세 정보를 조회
        OrderItemDto orderItemDto = orderService.getOrderDtails(orderId, email);

        // 주문 상세 정보를 클라이언트에 반환
        return ResponseEntity.ok(orderItemDto);
    }

    // 주문 취소
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable("orderId") Long orderId, @RequestParam("email") String email){
        try{
            // 주문 취소 처리
            orderService.cancelOrder(orderId, email);
            return ResponseEntity.ok("주문이 취소되었습니다.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문취소 실패 : " + e.getMessage());
        }
    }
}
