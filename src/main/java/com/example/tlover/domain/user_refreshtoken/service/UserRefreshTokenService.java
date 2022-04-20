package com.example.tlover.domain.user_refreshtoken.service;

import com.example.tlover.domain.user.entity.User;
import com.example.tlover.domain.user_refreshtoken.entity.UserRefreshToken;

public interface UserRefreshTokenService {

    long insertRefreshToken(String refreshJwt, User user);
}
