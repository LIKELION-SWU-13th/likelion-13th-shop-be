package com.likelion13th.shop.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PaymentDto {
    private Long paymentId;
    private Long orderId;
    private int amount;
    private String method;
    private String paymentStatus;
    private LocalDateTime paidBy;
}
