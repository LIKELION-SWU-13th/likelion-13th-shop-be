package com.likelion13th.shop.service;

import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입
    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    // 중복 회원 확인
    private void validateDuplicateMember(Member member) {
        Member validateMember = memberRepository.findByEmail(member.getEmail());
        if (validateMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
