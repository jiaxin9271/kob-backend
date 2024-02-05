package com.kob.backend.config;

import com.kob.backend.config.filter.JwtAuthenticationTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


/**
 * @Description: 放行公开接口，对其他接口进行鉴权
 * @Author: chenjx
 * @Date: 2023/5/5
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    private final String[] PERMIT_ALL = {
            "/swagger-ui.html",
            "/swagger-ui/*",
            "/v3/api-docs/*",
            "/error",
            "/api/user/account/token",
            "/api/user/account/register",
            "/api/user/account/acwing/acapp/apply_code",
            "/api/user/account/acwing/acapp/receive_code",
            "/api/user/account/acwing/web/apply_code",
            "/api/user/account/acwing/web/receive_code"
    };

    private final String[] PERMIT_LOCAL = {
            "/api/pk/start/game",
            "/api/pk/receive/bot/move"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*
     * @Description: 跨域
     */
    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    /**
     * @Description: 修改过滤连，在最前面加上jwt过滤链。同时设置放行链接。
     * @Param http:
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable() // 关闭 csrf 防护
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers(PERMIT_ALL).permitAll()  // 放置公开链接
//                .requestMatchers(PERMIT_LOCAL).permitAll()  // 放置公开链接
//                .anyRequest().authenticated()  // 其他接口进行鉴权
//                .and()
//                .cors().configurationSource(corsConfigurationSource()); // 跨域
        http.csrf().disable() // 关闭 csrf 防护
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers(PERMIT_ALL).permitAll()  // 放置公开链接
                .requestMatchers(PERMIT_LOCAL).hasIpAddress("127.0.0.1")
                .anyRequest().authenticated()  // 其他接口进行鉴权
                .and()
                .cors().configurationSource(corsConfigurationSource()); // 跨域

        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class); // 添加jwt过滤链
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/websocket/**");
    }
}
