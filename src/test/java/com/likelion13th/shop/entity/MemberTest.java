package com.likelion13th.shop.entity;

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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.properties")
class MemberTest {
    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    @DisplayName("Auditing 테스트")
    @WithMockUser(username = "dakyeom", roles = "USER")
    public void auditingTest() {
        Member member = new Member();
        memberRepository.save(member);

        em.flush();
        em.clear();

        Member savedMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("멤버 없음"));

        System.out.println("register time : " + savedMember.getRegTime());
        System.out.println("update time : " + savedMember.getUpdateTime());
        System.out.println("creator : " + savedMember.getCreatedBy());
        System.out.println("modifier : " + savedMember.getModifiedBy());
    }
}