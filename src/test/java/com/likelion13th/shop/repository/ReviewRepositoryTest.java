package com.likelion13th.shop.repository;

import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.entity.Review;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Test
    @DisplayName("리뷰 생성 테스트")
    public void createReviewTest(){

        Review review = new Review();

        review.setTitle("리뷰");
        review.setContent("굿이에요");
        review.setRating(5);
        review.setCreatedBy(LocalDateTime.now());
        review.setModifiedBy(LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);

        // 결과 출력
        System.out.println(savedReview.toString());
    }
}
