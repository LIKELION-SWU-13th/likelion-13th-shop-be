package com.likelion13th.shop.entity;
import com.likelion13th.shop.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.test.context.support.WithMockUser;

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
    @WithMockUser(username = "boyun", roles = "USER")
    public void auditingTest() {
        // member 생성 후 저장
        Member member = new Member();
        memberRepository.save(member);

        em.flush();
        em.clear();

        // 생성한 member id로 조회하기(+에러처리)
        Member findMember = memberRepository.findById(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        System.out.println("register time :  " + findMember.getRegTime());
        System.out.println("update time :  " + findMember.getUpdateTime());
        System.out.println("creater :  " + findMember.getCreatedBy());
        System.out.println("modifier :  " + findMember.getModifiedBy());
    }
}
