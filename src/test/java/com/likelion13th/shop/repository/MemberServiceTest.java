package com.likelion13th.shop.repository;

import com.likelion13th.shop.dto.MemberFormDto;
import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.service.MemberService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@TestPropertySource("classpath:application.properties")
class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setName("김민서");
        memberFormDto.setEmail("linaeevery0407@naver.com");
        memberFormDto.setPassword(passwordEncoder.encode("linaeevery0407"));
        memberFormDto.setAddress("경기도 남양주시 별네동");

        Member newMember = Member.createMember(memberFormDto, passwordEncoder);
        return newMember;
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMember() {
        Member member = this.createMember();
        Member member1 = memberService.saveMember(member);

        assertEquals(member.getEmail(), member1.getEmail());
        assertEquals(member.getPassword(), member1.getPassword());
        assertEquals(member.getName(), member1.getName());
        assertEquals(member.getAddress(), member1.getAddress());
    }

    @Test
    @DisplayName("중복 회원가입 테스트")
    public void setDuplicateMemberTest() {
        Member member1 = createMember();
        Member member2 = createMember();

        memberService.saveMember(member1);

        Throwable e = assertThrows(IllegalStateException.class, () -> memberService.saveMember(member2));

        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }
}
