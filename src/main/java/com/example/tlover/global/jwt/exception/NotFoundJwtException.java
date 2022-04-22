package com.example.tlover.global.jwt.exception;


public class NotFoundJwtException extends JwtException{

    public NotFoundJwtException() {
        super(JwtExceptionList.NOTFOUNDJWT.getCODE(),
                JwtExceptionList.NOTFOUNDJWT.getHttpStatus(),
                JwtExceptionList.NOTFOUNDJWT.getMESSAGE()
        );
    }

}
