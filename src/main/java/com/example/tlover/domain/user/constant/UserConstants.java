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

    @Getter
    @AllArgsConstructor
    public enum ENaverExceptionMessage {
        eNaverApiResponseExceptionMessage("NAVER API 응답을 읽는데 실패했습니다."),
        eNaverAuthenticationFailedExceptionMessage("Naver 인증에 실패했습니다."),
        eNaverPermissionExceptionMessage("Naver API 호출 권한이 없습니다."),
        eNaverNotFoundExceptionMessage("Naver API 검색 결과가 없습니다."),
        eNaverApiUrlExceptionMessage("NAVER API URL이 잘못되었습니다. : "),
        eNaverConnectionExceptionMessage("NAVER와의 연결이 실패했습니다. : ");
        private final String value;
    }
}
