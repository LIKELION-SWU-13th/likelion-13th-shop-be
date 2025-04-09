package com.likelion13th.shop.repository;

import com.likelion13th.shop.entity.Payment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
class PaymentRepositoryTest {
    @Autowired
    PaymentRepository paymentRepository;

    @Test
    @DisplayName("결제수단 테스트")
    public void createPaymentTest(){
        // Payment 객체 세팅
        Payment payment = new Payment();

        // Payment 객체에 결제 금액, 결제 수단 세팅
        payment.setAmount(50000);
        payment.setMethod("신용카드");

        // paymentRepository save 메서드 사용해서 해당 객체 저장
        Payment savedPayment = paymentRepository.save(payment);

        // System.out.println() 함수로 객체 출력
        // (Payment 엔티티에 @ToString 어노테이션 추가 후 payment.toString()을 출력)
        System.out.println(savedPayment);

        // 0407 3차 문제. toString() 오버라이딩 안 해서
    }
}