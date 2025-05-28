package com.likelion13th.shop.repository;

import com.likelion13th.shop.entity.Cart;
import com.likelion13th.shop.entity.CartItem;
import com.likelion13th.shop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndItemId(Long cartItemID, Long itemID);
}