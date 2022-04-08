package com.example.tlover.global.sms.exception;


import static com.example.tlover.global.sms.exception.SmsExceptionMessage.*;

public class HyphenPhoneNumException extends RuntimeException{
    public HyphenPhoneNumException(){super(SmsExceptionMessage.HYPHEN_PHONENUM_EXCEPTION_MESSAGE.getMessage());}
}