package com.example.tlover.domain.user.service;

import com.example.tlover.domain.user.dto.KakaoLoginRequest;
import com.example.tlover.domain.user.dto.LoginResponse;

public interface OAuth2UserServiceKakao {
    LoginResponse validateKakaoAccessToken(KakaoLoginRequest kakaoLoginRequest);
}
