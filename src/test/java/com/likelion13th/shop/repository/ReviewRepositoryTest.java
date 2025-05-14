package com.likelion13th.shop.repository;

import com.likelion13th.shop.entity.Review;
import jakarta.transaction.Transactional;

import net.bytebuddy.build.ToStringPlugin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    @DisplayName("리뷰 테스트")
    public void createReviewTest(){
        //리뷰
        Review review = new Review();
        review.setRating("5/5");
        review.setContent("예뻐요");
        Review savedReview = reviewRepository.save(review);
        System.out.println(review.toString());
    }


}