package com.likelion13th.shop.entity;

import com.likelion13th.shop.ReviewFormDto.ReviewFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "review")
@Getter @Setter @ToString
public class Review {
    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String title;
    private String content;

    public static Review createReview(ReviewFormDto reviewFormDto) {
        Review review = new Review();
        review.setName(reviewFormDto.getName());
        review.setTitle(reviewFormDto.getTitle());
        review.setContent(reviewFormDto.getContent());
        return review;
    }
}