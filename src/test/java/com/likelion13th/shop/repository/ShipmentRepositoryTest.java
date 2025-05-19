package com.likelion13th.shop.repository;

import com.likelion13th.shop.entity.Shipment;
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
class ShipmentRepositoryTest {
    @Autowired
    ShipmentRepository shipmentRepository;

    @Test
    @DisplayName("배송 테스트")
    public void createShipment() {
        // Shipment 객체 세팅
        Shipment shipment = new Shipment();

        // Shipment 객체에 송장 번호 세팅
        shipment.setTrackingNum("123-456789");

        // shipmentRepository save 메서드 사용해서 해당 객체 저장
        Shipment savedShipment = shipmentRepository.save(shipment);

        // System.out.println() 함수로 객체 출력
        // (Shipment 엔티티에 @ToString 어노테이션 추가 후 shipment.toString()을 출력)
        System.out.println(savedShipment);
    }
}