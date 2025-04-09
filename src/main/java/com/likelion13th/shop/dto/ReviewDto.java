package com.likelion13th.shop.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private String title;
    private String content;
    private int rating;
}