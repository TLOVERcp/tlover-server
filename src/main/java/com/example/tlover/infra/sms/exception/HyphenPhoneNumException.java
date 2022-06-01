package com.example.tlover.infra.sms.exception;


public class HyphenPhoneNumException extends RuntimeException{
    public HyphenPhoneNumException(){super(SmsExceptionMessage.HYPHEN_PHONENUM_EXCEPTION_MESSAGE.getMessage());}
}