package com.likelion13th.shop.repository;

import com.likelion13th.shop.dto.MemberFormDto;
import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

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

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();

        memberFormDto.setName("김하나");
        memberFormDto.setEmail("asdf@swu.ac.kr");
        memberFormDto.setPassword("asdf1234"); // 8자
        memberFormDto.setAddress("서울 노원구");

        Member member = Member.createMember(memberFormDto, passwordEncoder);
        // Member 엔티티의 createMember() 메소드 사용하여 객체 생성 후 반환

        return member;
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void saveMember(){
        Member member1 = this.createMember(); // createMember()로 회원 객체 생성
        Member member2 = memberService.saveMember(member1); // DB에 저장

        // assertEquals로 생성한 객체와 저장한 객체의 데이터가 일치하는지 개별 비교
        assertEquals(member1.getName(), member2.getName());
        assertEquals(member1.getAddress(), member2.getAddress());
        assertEquals(member1.getEmail(), member2.getEmail());
        assertEquals(member1.getPassword(), member2.getPassword());
    }

    @Test
    @DisplayName("중복 회원가입 테스트")
    public void setDuplicateMemberTest() {
        Member member1 = createMember();
        Member member2 = createMember();

        memberService.saveMember(member1);

        Throwable e = assertThrows(IllegalStateException.class, () -> {
                memberService.saveMember(member2);
        });

        assertEquals("이미 가입된 회원입니다.", e.getMessage());

    }
}
