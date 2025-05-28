package com.likelion13th.shop.repository;

import com.likelion13th.shop.entity.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    @DisplayName("리뷰 테스트")
    public void createReviewTest(){
        // Review 객체 세팅
        Review review = new Review();

        // Review 객체에 이름, 평점, 내용 세팅
        review.setName("김하나");
        review.setRating(5);
        review.setContent("너무 좋아요");

        // reviewRepository save 메서드 사용해서 해당 객체 저장
        Review savedReview = reviewRepository.save(review);

        // System.out.println() 함수로 객체 출력
        // (Review 엔티티에 @ToString 어노테이션 추가 후 review.toString()을 출력)
        System.out.println(savedReview);
    }
}