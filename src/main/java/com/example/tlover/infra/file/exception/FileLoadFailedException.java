package com.example.tlover.infra.file.exception;

public class FileLoadFailedException extends IllegalArgumentException {
    private static final String MESSAGE = "파일 불러오기에 실패했습니다.";
    public FileLoadFailedException() {super(MESSAGE);}
}
