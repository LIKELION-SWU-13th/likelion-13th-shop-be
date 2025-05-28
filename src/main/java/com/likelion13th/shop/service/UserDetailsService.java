package com.likelion13th.shop.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDetailsService {
    //요청받은 사용자 정보로 DB 조회하여 UserDetails 인터페이스로 반환
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
