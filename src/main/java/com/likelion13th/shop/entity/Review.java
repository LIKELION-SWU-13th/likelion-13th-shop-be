package com.likelion13th.shop.entity;

import com.likelion13th.shop.dto.ReviewDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString

@Entity
@Table(name="review")
@Getter
@Setter
public class Review {
    @Id
    @Column(name = "review_id") // 리뷰 ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 외래키 설정
    @JoinColumn(name = "member_id")
    private Member member; // Member 매핑
    @ManyToOne // 외래키 설정
    @JoinColumn(name = "item_id")
    private Item item; // Item 매핑

    private String name;
    private String content; // 후기 내용
    private int rating; // 평점

    private LocalDateTime createdBy; // 등록 시간
    private LocalDateTime modifiedBy; // 수정 시간

    public static Review createReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.setName(reviewDto.getName());
        review.setRating(reviewDto.getRating());
        review.setContent(reviewDto.getContent());
        return review;
    }
}
