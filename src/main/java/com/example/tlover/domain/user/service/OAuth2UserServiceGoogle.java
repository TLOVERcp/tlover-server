package com.example.tlover.domain.user.service;

import com.example.tlover.domain.user.dto.GoogleLoginRequest;
import com.example.tlover.domain.user.dto.LoginResponse;
import com.example.tlover.domain.user.dto.NaverLoginRequest;

public interface OAuth2UserServiceGoogle {

    LoginResponse validateGoogleAccessToken(GoogleLoginRequest naverLoginRequest);

}
