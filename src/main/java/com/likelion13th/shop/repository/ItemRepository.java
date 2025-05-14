package com.likelion13th.shop.repository;

import com.likelion13th.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    // 실습 2 - 상품명으로 상품 조회
    List<Item> findByItemName(String itemName);

    // 실습 3 - 상품 내림차순 정렬
    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    // 실습 4 - 상세 설명 조회 + 가격 내림차순
    @Query("select i from Item i where i.itemDetail like " + "%:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);

    // 과제 1 - 상품 키워드 검색
    List<Item> findByItemNameContainingIgnoreCase(String itemName);
}
