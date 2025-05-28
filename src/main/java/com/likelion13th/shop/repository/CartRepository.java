package com.likelion13th.shop.repository;

import com.likelion13th.shop.dto.CartDetailDto;
import com.likelion13th.shop.entity.Cart;
import com.likelion13th.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMember(Member member);

}
