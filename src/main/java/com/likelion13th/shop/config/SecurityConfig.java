package com.likelion13th.shop.config;

import com.likelion13th.shop.service.MemberService;
import com.likelion13th.shop.config.CustomAccessDeniedHandler;
import com.likelion13th.shop.config.CustomAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MemberService memberService;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(MemberService memberService,
                          CustomAccessDeniedHandler accessDeniedHandler) {
        this.memberService = memberService;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .userDetailsService(memberService)
                .formLogin(form -> form
                        .loginPage("/members/login")
                        .defaultSuccessUrl("/")
                        .usernameParameter("email")
                        .failureUrl("/members/login/error")
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                        .logoutSuccessUrl("/")
                );

        // 권한별 접근 설정
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/members/login").anonymous()
                        .requestMatchers("/members/logout").authenticated()
                        .requestMatchers("/members/admin/**").hasRole("ADMIN") // 🔥 슬래시 주의!
                        .anyRequest().permitAll()
                );

        // 인증 실패 + 권한 거부 처리 핸들러 등록
        http
                .exceptionHandling(error -> error
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(accessDeniedHandler)
                );

        return http.build();
    }
}

