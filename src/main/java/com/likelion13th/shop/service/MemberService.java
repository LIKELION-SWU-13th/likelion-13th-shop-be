package com.likelion13th.shop.service;

import com.likelion13th.shop.entity.Cart;
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

    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member); //피피티 빈칸
    }

    private void validateDuplicateMember(Member member) {
        //이메일을 통해 DB에 동일 회원이 존재하는지 확인
        Member newMember = memberRepository.findByEmail(member.getEmail());
        if (newMember != null){ //회원이 이미 존재한다면 에러
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
