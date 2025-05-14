package com.likelion13th.shop.repository;

import com.likelion13th.shop.entity.Order;
import com.likelion13th.shop.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByMemberEmail(String email);
}
