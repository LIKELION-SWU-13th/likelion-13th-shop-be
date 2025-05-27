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

import java.util.Optional;

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
        memberRepository.save(member);

        em.flush();
        em.clear();

        Member auditingMember = Optional.ofNullable(em.find(Member.class, member.getId()))
                .orElseThrow(() -> new IllegalStateException("회원을 찾을 수 없습니다."));


        System.out.println("register time: " + auditingMember.getRegTime());
        System.out.println("update time: " + auditingMember.getUpdateTime());
        System.out.println("creater: " + auditingMember.getCreatedBy());
        System.out.println("modifier: " + auditingMember.getModifiedBy());
    }
}
