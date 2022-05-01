package com.example.tlover.global.jwt.exception;

public class ExpireAccessException extends JwtException{
    public ExpireAccessException(){
        super(JwtExceptionList.EXPIRE_ACCESSTOKEN.getCODE(),
                JwtExceptionList.EXPIRE_ACCESSTOKEN.getHttpStatus(),
                JwtExceptionList.EXPIRE_ACCESSTOKEN.getMESSAGE()
        );
    }
}
