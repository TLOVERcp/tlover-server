package com.example.tlover.domain.user.service;

import com.example.tlover.domain.user.entity.User;


public interface UserRefreshTokenService {

    long insertRefreshToken(String refreshJwt, User user);
}
