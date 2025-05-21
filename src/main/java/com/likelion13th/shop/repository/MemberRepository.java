package com.likelion13th.shop.repository;

import com.likelion13th.shop.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
//    void save(Member member);
}

//alt+insert
//Member는 무슨 매개변수지?
