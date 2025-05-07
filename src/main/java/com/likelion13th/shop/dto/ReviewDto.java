package com.likelion13th.shop.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ReviewDto {
    private String name;
    private Long reviewId;
    private Long memberId;
    private Long itemId;
    private int rating;
    private String content;
    private String createdBy;
    private String modifiedBy;
}
