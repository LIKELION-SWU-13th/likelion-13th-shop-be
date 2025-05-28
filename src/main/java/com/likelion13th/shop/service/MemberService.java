package com.likelion13th.shop.service;

import com.likelion13th.shop.entity.Member;
import com.likelion13th.shop.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        System.out.println("111");
        Member member = memberRepository.findByEmail(email);
        System.out.println("111");

        if(member == null){
            System.out.println("222");
            throw new UsernameNotFoundException(email);
        }
        System.out.println("111");
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }
    // 회원가입
    public Member saveMember(Member member){
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }
    // 중복 회원 확인
    private void validateDuplicateMember(Member member){
        Member findmember = memberRepository.findByEmail(member.getEmail());
        if(findmember != null){
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
