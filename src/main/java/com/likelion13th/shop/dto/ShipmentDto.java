package com.likelion13th.shop.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ShipmentDto {
    private Long shipmentId;
    private Long ordered;
    private String trackingNum;
    private String shipmentStatus;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
}
