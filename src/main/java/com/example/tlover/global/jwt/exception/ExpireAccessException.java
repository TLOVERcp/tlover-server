package com.example.tlover.global.jwt.exception;

public class ExpireAccessException  extends JwtException{
    public ExpireAccessException(){
        super(JwtExceptionList.EXPIRE_REFRESHTOKEN.getCODE(),
                JwtExceptionList.EXPIRE_REFRESHTOKEN.getHttpStatus(),
                JwtExceptionList.EXPIRE_REFRESHTOKEN.getMESSAGE()
        );
    }
}
