package com.example.tlover.infra.file.exception;

public class FileExtensionException extends IllegalArgumentException {
    private static final String MESSAGE = "이미지 파일이 아닙니다.";
    public FileExtensionException() {super(MESSAGE);}
}
