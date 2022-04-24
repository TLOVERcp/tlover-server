package com.example.tlover.domain.user_region.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class UserRegionConstants {

    @Getter
    @AllArgsConstructor
    public enum EUserRegionResponseMessage {
        eSuccessUpdateUserRegionMessage("사용자 관심지역 수정을 성공했습니다.");
        private final String value;
    }
}
