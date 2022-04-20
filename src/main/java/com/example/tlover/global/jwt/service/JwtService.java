package com.example.tlover.global.jwt.service;

import javax.validation.Valid;

public interface JwtService {
    String createAccessJwt(@Valid String UserId);
    String createRefreshJwt(@Valid String UserId);
    String resolveAccessToken();
    Long resolveRefreshToken();
    String getLoginId();
}
