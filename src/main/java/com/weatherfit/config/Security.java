package com.weatherfit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class Security {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())               // CSRF 비활성화 (개발용)
                .formLogin(form -> form.disable())          // Spring 기본 로그인 폼 비활성화
                .httpBasic(basic -> basic.disable())        // HTTP Basic 인증 비활성화
                .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()               // 모든 요청 허용
                );

        return http.build();
    }
}
