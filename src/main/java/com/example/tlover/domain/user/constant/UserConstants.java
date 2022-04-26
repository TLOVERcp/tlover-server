package com.example.tlover.domain.user.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserConstants {

    @Getter
    @AllArgsConstructor
    public enum EOAuth2UserServiceImpl {

        eBearer("Bearer "),
        eAuthorization("Authorization"),
        eGetMethod("GET"),
        eResponse("response"),
        eNameAttribute("name"),
        eGoogleIdToken("id_token"),
        eEmailAttribute("email"),
        eNaverProfileImageAttribute("profile_image"),
        eGoogleProfileImageAttribute("picture"),

        eKakaoProfileImageAttribute("profile_image"),
        eKakaoProperties("properties"),
        eKakaoAcount("kakao_account"),

        eGoogleTokenInvalid("잘못된 토큰입니다.");
        private final String value;
    }

    @Getter
    @AllArgsConstructor
    public enum ESocialProvider{
        eGoogle,
        eNaver,
        eKakao,
        eApp;
    }

}
