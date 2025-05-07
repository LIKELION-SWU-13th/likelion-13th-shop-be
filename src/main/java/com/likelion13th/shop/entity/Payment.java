package com.likelion13th.shop.entity;

import com.likelion13th.shop.dto.PaymentDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString

@Entity
@Table(name="payment")
@Getter
@Setter
public class Payment {
    @Id
    @Column(name = "payment_id") // 결제 정보 ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 외래키 설정
    @JoinColumn(name = "order_id")
    private Order order; // Order 매핑

    private String method; // 결제 수단
    private int amount; // 결제 금액

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private LocalDateTime paidBy; // 결제 일시

    public static Payment createPayment(PaymentDto paymentDto) {
        Payment payment = new Payment();
        payment.setAmount(paymentDto.getAmount());
        payment.setMethod(paymentDto.getMethod());
        payment.setStatus(PaymentStatus.valueOf(paymentDto.getPaymentStatus()));
        return payment;
    }
}