package com.likelion13th.shop.repository;

import com.likelion13th.shop.entity.Item;
import com.likelion13th.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    // 상품명으로 상품 조회
    List<Item> findByItemName(String itemName);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    List<Item> findByItemNameContainingIgnoreCase(String itemName);

    @Query("SELECT i FROM Item i WHERE i.itemDetail like" + "%:itemDetail% ORDER BY i.price DESC")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    List<Item> findByItemDetailOrderByPriceDesc(@Param("itemDetail") String itemDetail);
}