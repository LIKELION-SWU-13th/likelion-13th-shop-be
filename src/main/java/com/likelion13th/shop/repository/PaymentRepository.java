package com.likelion13th.shop.repository;

import com.likelion13th.shop.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
