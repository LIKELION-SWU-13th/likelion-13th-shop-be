package com.likelion13th.shop.service;

import com.likelion13th.shop.dto.MemberFormDto;
import com.likelion13th.shop.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@Rollback(true)
@TestPropertySource("classpath:application.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setName("양보윤");
        memberFormDto.setEmail("boyunyang@naver.com");
        memberFormDto.setPassword("boyun@02");
        memberFormDto.setAddress("서울시 마포구");
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        return Member.createMember(memberFormDto, passwordEncoder);
    }
}

  /* @Test
    @DisplayName("회원가입 테스트")
    public void saveMember(){
        Member member = this.createMember();
        Member member1 = memberService.saveMember(member);

        assertEquals(member.getName(), member1.getName());
        assertEquals(member.getEmail(), member1.getEmail());
        assertEquals(member.getAddress(), member1.getAddress());
        assertEquals(member.getPassword(), member1.getPassword());
        assertEquals(member.getRole() , member1.getRole());
    }

    @Test
    @DisplayName("중복 회원가입 테스트")
    public void setDuplicateMemberTest() {

        Member member1 = createMember();
        Member member2 = createMember();

        // member1 저장
        memberService.saveMember(member1);


        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member2); //member2 저장
        });

        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }




}*/