package com.likelion13th.shop.entity;

import com.likelion13th.shop.constant.Role;
import com.likelion13th.shop.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Entity
@Table(name = "member")
@Getter @Setter
@ToString
public class Member extends BaseTime{
    // PK 설정
    @Id
    @Column (name = "member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true)
    private  String email;
    private  String password;
    private  String address;

    @Enumerated(EnumType.STRING)
    private Role role;

    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String modifiedBy;

    private String itemImg;
    private String itemImgPath;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName()); // 이름
        member.setEmail(memberFormDto.getEmail()); // 이메일

        String pwd = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(pwd); // 비밀번호

        member.setRole(Role.USER);
        member.setAddress(memberFormDto.getAddress()); // 주소

        return member;
    }
}
