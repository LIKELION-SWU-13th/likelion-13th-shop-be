package com.likelion13th.shop.entity;
import aj.org.objectweb.asm.ConstantDynamic;
import com.likelion13th.shop.constant.Role;
import com.likelion13th.shop.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
@Entity
@Table(name="member")
@Getter @Setter
@ToString
public class Member extends BaseTime{
        // PK 설정
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

        public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        String pwd = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(pwd);
        member.setRole(Role.ADMIN);
        member.setAddress(memberFormDto.getAddress());



        return member;
    }
}


