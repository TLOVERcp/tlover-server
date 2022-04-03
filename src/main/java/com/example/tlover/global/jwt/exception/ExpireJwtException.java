package com.example.tlover.global.jwt.exception;

public class ExpireJwtException extends RuntimeException{
    public ExpireJwtException(String s){
        super(s);
    }
}
