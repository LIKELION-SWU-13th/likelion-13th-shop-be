package com.likelion13th.shop.repository;

import com.likelion13th.shop.entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

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
    @WithMockUser(username = "hana", roles = "USER")
    public void auditingTest(){
        // member 생성 후 저장
        Member member = new Member();
        member = memberRepository.save(member);

        em.flush();
        em.clear();

        // 생성한 member id로 조회하기 (+에러 처리)
        Member findMember = memberRepository.findById(member.getId())
                        .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        System.out.println("register time: " + findMember.getRegTime());
        System.out.println("update time: " + findMember.getUpdateTime());
        System.out.println("creater: " + findMember.getCreatedBy());
        System.out.println("modifier: " + findMember.getModifiedBy());

    }
}
