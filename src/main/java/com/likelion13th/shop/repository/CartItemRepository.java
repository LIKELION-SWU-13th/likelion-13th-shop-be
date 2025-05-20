package com.likelion13th.shop.repository;

import com.likelion13th.shop.CartDetailDto.CartDetailDto;
import com.likelion13th.shop.entity.Cart;
import com.likelion13th.shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndItemId(Long cart_id, Long item_id);
}