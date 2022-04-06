package com.example.tlover.global.jwt.Secret;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecretKey {

    public static String JWT_ACCESS_SECRET_KEY;
    public static String JWT_REFRESH_SECRET_KEY;

    @Value("${spring.jwt.access-key}")
    private void setJWT_ACCESS_SECRET_KEY(String JWT_ACCESS_SECRET_KEY) {
        this.JWT_ACCESS_SECRET_KEY = JWT_ACCESS_SECRET_KEY;
    }

    @Value("${spring.jwt.refresh-key}")
    private void setJWT_REFRESH_SECRET_KEY(String JWT_REFRESH_SECRET_KEY) {
        this.JWT_REFRESH_SECRET_KEY = JWT_REFRESH_SECRET_KEY;
    }

}
