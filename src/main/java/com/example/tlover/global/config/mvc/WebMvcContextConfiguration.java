package com.example.tlover.global.config.mvc;

import com.example.tlover.global.interceptor.JwtInterceptor;
import com.example.tlover.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebMvcContextConfiguration implements WebMvcConfigurer {

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
                        "/api/v1/users/kakao-login",
                        "/api/v1/sms/send",
                        "/api/v1/sms/find-password",
                        "/api/v1/sms/find-id"
                        );
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
