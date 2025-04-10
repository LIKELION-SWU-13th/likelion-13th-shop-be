package com.likelion13th.shop.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import com.likelion13th.shop.entity.Member;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 테스트")
    public void createMemberTest(){
        Member member = new Member();
        member.setName("우예빈");
        member.setEmail("test@naver.com");
        member.setPassword("abc123");
        member.setAddress("서울특별시 노원구");

        Member savedMember = memberRepository.save(member);
        System.out.println(savedMember);
    }
}