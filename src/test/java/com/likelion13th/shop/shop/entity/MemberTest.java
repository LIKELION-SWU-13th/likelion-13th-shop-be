package com.likelion13th.shop.shop.entity;

import com.likelion13th.shop.entity.Cart;
import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import com.likelion13th.shop.dto.MemberFormDto;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
public class MemberTest {
    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Auditing 테스트")
    @WithMockUser(username = "yeeun", roles = "USER")
    public void auditingTest() {
        Member member = new Member();
        Member savedMember = memberRepository.save(member);

        em.flush();
        em.clear();

        Member member1 = memberRepository.findById(savedMember.getId())
                .orElseThrow(() -> new RuntimeException("멤버를 찾지 못함."));

        System.out.println("register time: " + member1.getRegTime());
        System.out.println("update time: " + member1.getUpdateTime());
        System.out.println("creater: " + member1.getCreatedBy());
        System.out.println("modifier: " + member1.getModifiedBy());
    }
}
