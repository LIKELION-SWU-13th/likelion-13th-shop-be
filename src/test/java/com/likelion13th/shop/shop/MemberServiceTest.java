package com.likelion13th.shop.shop;

import com.likelion13th.shop.dto.MemberFormDto;
import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.repository.MemberRepository;
import com.likelion13th.shop.service.MemberService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestPropertySource("classpath:application.properties")
public class MemberServiceTest {
    @PersistenceContext
    EntityManager em;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private MemberService memberService;

    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setName("제영임");
        memberFormDto.setEmail("jylion13@likelion13.kr");
        memberFormDto.setPassword(passwordEncoder.encode("030504"));
        memberFormDto.setAddress("서울시 성북구 종암로");
        Member member = new Member();
        member.createMember(memberFormDto);
        return member;
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMember(){
        Member member = createMember();
        Member savedMember = memberRepository.save(member);
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getEmail(), savedMember.getEmail());
    }

    @Test
    @DisplayName("중복 회원가입 테스트")
    public void setDuplicateMemberTest(){
        Member member1 = new Member();
        Member member2 = new Member();

        memberService.saveMember(member1);

        Throwable e = assertThrows(IllegalArgumentException.class, () -> {
            memberService.saveMember(member2);

        });

        assertEquals("이미 가입된 회원입니다. ", e.getMessage());
    }


}
