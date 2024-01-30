package com.kob.matchingsystem.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


/**
 * @Description: 放行公开接口，对其他接口进行鉴权
 * @Author: chenjx
 * @Date: 2023/5/5
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final String[] PERMIT_URL = {
            "/player/add/",
            "/player/remove/",
            "/error"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable() // 关闭 csrf 防护
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers(PERMIT_URL).hasIpAddress("127.0.0.1")
                .anyRequest().authenticated();  // 其他接口进行鉴权

//        http.csrf().disable() // 关闭 csrf 防护
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 会话
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers(PERMIT_URL).permitAll()  // 放置公开链接
//                .anyRequest().authenticated();  // 其他接口进行鉴权

        return http.build();
    }

}
