package com.example.tlover.global.jwt.exception;

public class ExpireRefreshException extends JwtException{
    public ExpireRefreshException(){
        super(JwtExceptionList.EXPIRE_REFRESHTOKEN.getCODE(),
                JwtExceptionList.EXPIRE_REFRESHTOKEN.getHttpStatus(),
                JwtExceptionList.EXPIRE_REFRESHTOKEN.getMESSAGE()
        );
    }
}
