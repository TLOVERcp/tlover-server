package com.example.tlover.global.config.mvc;

import com.example.tlover.global.interceptor.JwtInterceptor;
import com.example.tlover.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {

    private final JwtService jwtService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor(jwtService))
                .order(1)
                .addPathPatterns("/api/v1/**")
                .excludePathPatterns("/api/v1/users/login",
                        "/api/v1/users/signupUser",
                        "/api/v1/users/duplicate-check",
                        "/api/v1/users/nickname-duplicate-check",
                        "/api/v1/users/find-id",
                        "/api/v1/users/find-password",
                        "/api/v1/users/naver-login",
                        "/api/v1/users/google-login",
                        "/api/v1/users/kakao-login"
                        );
    }
}
