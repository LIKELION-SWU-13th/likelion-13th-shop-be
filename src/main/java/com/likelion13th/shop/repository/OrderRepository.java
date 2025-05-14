package com.likelion13th.shop.repository;


import com.likelion13th.shop.dto.OrderItemDto;
import com.likelion13th.shop.entity.Order;
import com.likelion13th.shop.entity.OrderItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByMemberEmail(String email);
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    List<OrderItem> orderItemList = new ArrayList<>();

//    void save(Order order);
}
