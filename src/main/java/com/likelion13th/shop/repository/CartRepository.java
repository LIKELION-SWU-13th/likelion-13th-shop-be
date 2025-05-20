package com.likelion13th.shop.repository;

import com.likelion13th.shop.dto.CartDetailDto;
import com.likelion13th.shop.entity.Cart;
import com.likelion13th.shop.entity.CartItem;
import com.likelion13th.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByMember(Member member);

    @Query("select new com.likelion13th.shop.dto.CartDetailDto(ci.id, ci.item.itemName, ci.item.price, ci.count, ci.item.itemImgPath)" +
            "from CartItem ci " +
            "where ci.cart.id = :cartId")
    List<CartDetailDto> findCartOrderList(@Param("cartId") Long cartId);
}
