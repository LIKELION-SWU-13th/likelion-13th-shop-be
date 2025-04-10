package com.likelion13th.shop.entity;

import com.likelion13th.shop.MemberFormDto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import com.likelion13th.shop.constant.Role;
import lombok.ToString;

@Entity
@Table(name="member") // 객체와 테이블 매핑
@Getter @Setter @ToString
public class Member {
    @Id
    @Column (name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK 선언

    private String name;

    @Column(unique = true)
    private String email;
    private String password;
    private String address;

    @Enumerated(EnumType.STRING) // 역할 지정
    private Role role;

    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;

    public static Member createMember(MemberFormDto memberFormDto){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setPassword(memberFormDto.getPassword());
        member.setAddress(memberFormDto.getAddress());
        return member;
    }
}