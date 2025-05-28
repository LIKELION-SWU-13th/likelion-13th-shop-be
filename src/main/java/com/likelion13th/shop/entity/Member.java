package com.likelion13th.shop.entity;

import com.likelion13th.shop.dto.MemberFormDto;
import com.likelion13th.shop.constant.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
@ToString
public class Member extends BaseTime{
    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;
    private String password;
    private String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String modifiedBy;

    public static Member createMember(MemberFormDto memberFormDto) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setPassword(memberFormDto.getPassword());
        member.setAddress(memberFormDto.getAddress());
        return member;
    }

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        String pwd = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(pwd);
        member.setRole(Role.USER);
        member.setAddress(memberFormDto.getAddress());

        return member;
    }


}
