package com.likelion13th.shop.service;

import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        // 멤버가 존재하지 않는다면
        if (member == null) {
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(String.valueOf(member.getRole()))
                .build();
    }

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
