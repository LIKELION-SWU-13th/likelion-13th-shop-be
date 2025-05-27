package com.likelion13th.shop.shop.entity;

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
public class MemberServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setName(memberFormDto.getName());
        memberFormDto.setEmail(memberFormDto.getEmail());
        memberFormDto.setAddress(memberFormDto.getAddress());
        memberFormDto.setPassword(memberFormDto.getPassword());

        return Member.createMember(memberFormDto);
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMember(){
        Member member = this.createMember();
        Member member1 = memberService.saveMember(member);
        //memberService.saveMember(member);

        assertEquals(member.getName(), member1.getName());
        assertEquals(member.getEmail(), member1.getEmail());
        assertEquals(member.getAddress(), member1.getAddress());
        assertEquals(member.getPassword(), member1.getPassword());
        assertEquals(member.getRole(), member1.getRole());
    }

    @Test
    @DisplayName("중복 회원가입 테스트")
    public void setDuplicateMemberTest(){
        Member member1 = createMember();
        Member member2 = createMember();

        memberService.saveMember(member1);

        Throwable e = assertThrows(IllegalStateException.class, () -> {
            memberService.saveMember(member2);
        });

        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }
}
