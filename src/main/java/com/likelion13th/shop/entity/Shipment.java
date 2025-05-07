package com.likelion13th.shop.entity;

import com.likelion13th.shop.dto.ShipmentDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString

@Entity
@Table(name="shipment")
@Getter
@Setter
public class Shipment {
    @Id
    @Column(name = "shipment_id") // 배송 정보 ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 외래키 설정
    @JoinColumn(name = "order_id")
    private Order order; // Order 매핑

    private String trackingNum; // 송장 번호

    private LocalDateTime shippedAt; // 배송 시작일
    private LocalDateTime deliveredAt; // 배송 완료일

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    public static Shipment createShipment(ShipmentDto shipmentDto) {
        Shipment shipment = new Shipment();
        shipment.setTrackingNum(shipmentDto.getTrackingNum());
        shipment.setStatus(ShipmentStatus.valueOf(shipmentDto.getShipmentStatus()));
        return shipment;
    }
}
