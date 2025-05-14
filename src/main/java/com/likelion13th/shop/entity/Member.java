package com.likelion13th.shop.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDateTime;
@Entity
@Table(name="member")
@Getter @Setter
@ToString
public class Member extends BaseTime{
    // PK 설정
    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique=true)
    private String email;
    private String password;
    private String address;
    private LocalDateTime createdBy;
    private LocalDateTime modifiedBy;
}