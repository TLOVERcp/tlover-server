package com.example.tlover.domain.plan.exception;
public class NoAuthorityUserException extends RuntimeException {
        public NoAuthorityUserException() {super("해당 계획에 접근 권한이 없는 유저입니다.");}

}
