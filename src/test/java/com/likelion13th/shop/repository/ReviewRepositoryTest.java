package com.likelion13th.shop.repository;

import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.entity.Review;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    @DisplayName("리뷰 등록 테스트")
    public void createReviewTest() {
        // 리뷰 객체 생성
        Review review = new Review();

        // 리뷰 객체에 이름, 별점, 내용 세팅
        review.setName("이다겸");
        review.setRating(5);
        review.setContent("재구매 원해요");

        // reviewRespository의 save 메서드 사용해서 해당 객체 저장
        Review savedReview = reviewRepository.save(review);

        // System.out.println() 함수로 객체 출력
        // (Member 엔티티에 @ToString 어노테이션 추가 후 member.toString()을 출력)
        System.out.println(review.toString());
    }
}