package com.nhnacademy.inkbridge.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * class: SecurityConfig.
 * security 설정 클래스입니다.
 * @author minseo
 * @version 2/15/24
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * bcrypt + salt
     * 단방향 해시로 password를 저장하기 위함
     * DB저장은 절대 평문 패스워드로 하지말것 .
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
