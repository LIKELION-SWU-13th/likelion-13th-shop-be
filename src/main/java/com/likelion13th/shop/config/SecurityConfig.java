package com.likelion13th.shop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final LoginSuccessHandler loginSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http

                //로그인
                .formLogin(form -> form
                        .loginPage("/members/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(loginSuccessHandler)
                        .failureUrl("/members/login/error")
                )

                //로그아웃
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                        .logoutSuccessUrl("/")
                )

        //보안검사
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/admin/login").permitAll()
                        .requestMatchers("/admin/main").hasRole("ADMIN")
                        .requestMatchers("/admin/page").hasRole("ADMIN")

                        .requestMatchers("/members/login").anonymous()
                        .requestMatchers("/members/logout").authenticated()
                        .anyRequest().permitAll()
                )

        //인증 실패 시 대처 방법 커스텀

                .exceptionHandling(error -> error
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )
                .exceptionHandling(error -> error
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )
                .csrf(csrf -> csrf.disable());


        return http.build();
    }
}
