package com.likelion13th.shop.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberFormDto{
    private String name;

    @Column(unique = true)
    private String email;
    private String password;
    private String address;
}
