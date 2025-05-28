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
    @WithMockUser(username="yebin", roles="USER")
    public void auditingTest(){
        Member member = new Member();
        Member savedMember = memberRepository.save(member);

        em.flush();
        em.clear();

        Member testMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new IllegalStateException("멤버를 찾지 못했습니다."));

        System.out.println("register time: " + testMember.getRegTime());
        System.out.println("update time: " + testMember.getUpdateTime());
        System.out.println("creater: " + testMember.getCreatedBy());
        System.out.println("modifier: " + testMember.getModifiedBy());
    }
}
