package com.example.tlover.global.jwt.Secret;


import org.springframework.beans.factory.annotation.Value;

public class SecretKey {
    @Value("${jwt.access-key}")
    public static String JWT_ACCESS_SECRET_KEY;

    @Value("${jwt.refresh-key}")
    public static String JWT_REFRESH_SECRET_KEY;

}
