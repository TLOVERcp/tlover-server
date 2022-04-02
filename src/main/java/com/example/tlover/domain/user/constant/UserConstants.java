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
        eNaverNameAttribute("name"),
        eNaverEmailAttribute("email"),
        eNaverProfileImageAttribute("profile_image"),
        eNaverApiResponseException("NAVER API 응답을 읽는데 실패했습니다."),
        eNaverApiUrlException("NAVER API URL이 잘못되었습니다. : "),
        eNaverConnectionException("NAVER와의 연결이 실패했습니다. : ");
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
