package com.likelion13th.shop.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import com.likelion13th.shop.entity.Review;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
public class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    @DisplayName("리뷰 테스트")
    public void createReviewTest(){
        Review review = new Review();
        review.setName("리뷰작성자");
        review.setTitle("리뷰제목");
        review.setContent("좋아요");

        Review savedReview = reviewRepository.save(review);
        System.out.println(savedReview);
    }
}
