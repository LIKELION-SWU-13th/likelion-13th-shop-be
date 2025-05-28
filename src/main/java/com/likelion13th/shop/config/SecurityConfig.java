package com.likelion13th.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.likelion13th.shop.config.CustomAccessDeniedHandler;
import com.likelion13th.shop.config.CustomAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화에 사용
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin(form -> form
                        .loginPage("/members/login")              // 커스텀 로그인 페이지
                        .defaultSuccessUrl("/")                   // 로그인 성공 시 이동할 페이지
                        .usernameParameter("email")              // 로그인 시 사용할 필드
                        .failureUrl("/members/login/error")      // 로그인 실패 시 이동할 페이지
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) // 로그아웃 URL
                        .logoutSuccessUrl("/")                                              // 로그아웃 성공 시 이동할 페이지
                )
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/members/login").anonymous() // 로그인 페이지는 비로그인 사용자만 접근 가능
                        .requestMatchers("/members/logout").authenticated() // 로그아웃은 로그인한 사용자만
                        .requestMatchers("/admin").hasRole("ADMIN") // 관리자 전용 경로
                        .anyRequest().permitAll() // 그 외는 모두 허용
                )
                .exceptionHandling(error -> error
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 인증 실패 시 처리
                        .accessDeniedHandler(new CustomAccessDeniedHandler())           // 권한 부족(403) 처리
                );

        return http.build();
    }
}
