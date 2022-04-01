package com.example.trover.global.config.jwt.exception;

public class ExpireJwtException extends RuntimeException{
    public ExpireJwtException(String s){
        super(s);
    }
}
