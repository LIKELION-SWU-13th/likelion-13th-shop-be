package com.likelion13th.shop.entity;

import com.likelion13th.shop.dto.ReviewFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter @Setter
@ToString
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 리뷰 작성자
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    private String name;

    // 리뷰 대상 상품
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    // 별점
    @Column(nullable = false)
    private int rating;

    // 리뷰 내용
    @Column(nullable = false)
    private String content;

    // 작성일
    private LocalDateTime createdAt;

    // 수정일
    private LocalDateTime updatedAt;

    public static Review createReview(Member member, Item item, ReviewFormDto reviewFormDto) {
        Review review = new Review();
        review.setMember(member); // member 정보 : dto엔 없지만 서비스에서 받아옴
        review.setItem(item); // item 정보
        review.setName(reviewFormDto.getName()); // 이름
        review.setRating(reviewFormDto.getRating()); // 별점
        review.setContent(review.getContent()); // 내용
        return review;
    }
}
