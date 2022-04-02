package com.example.tlover.global.config.jwt.service;

import javax.validation.Valid;

public interface JwtService {
    String createAccessJwt(@Valid String UserId);
    String createRefreshJwt(@Valid String UserId);
    String resolveAccessToken();
    String resolveRefreshToken();
    String getUserId();
}
