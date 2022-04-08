package com.example.tlover.global.sms.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SmsExceptionMessage {
    HYPHEN_PHONENUM_EXCEPTION_MESSAGE("전화번호에 - 을 제거해주세요.");

    private final  String message;
}
