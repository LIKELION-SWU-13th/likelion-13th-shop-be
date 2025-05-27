package com.likelion13th.shop.dto;

import lombok.Getter;
import lombok.Setter;

// 리뷰 등록용 DTO
@Getter @Setter
public class ReviewFormDto {
    private String name;
    private int rating;
    private String content;
}
