package com.likelion13th.shop.entity;

import com.likelion13th.shop.dto.MemberFormDto;
import com.likelion13th.shop.dto.ReviewFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name="review")
@ToString
@Getter @Setter
public class Review {

    //pk설정
    @Id
    @Column (name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content; //후기
    private String rating; //평점
    private LocalDateTime createdBy; //생성일
    private LocalDateTime modifiedBy; //수정일

    //연관관계
    @ManyToOne
    @JoinColumn(name = "member_member_id")
    private Member member; //회원

    @ManyToOne
    @JoinColumn(name = "item_item_id")
    private Item item; //아이템과 매핑 어떤 거의 후기?


    public static Review createReview(ReviewFormDto reviewFormDto){
        Review review = new Review();
        review.setRating(reviewFormDto.getRating());
        review.setContent(reviewFormDto.getContent());
        return review;
    }
}
