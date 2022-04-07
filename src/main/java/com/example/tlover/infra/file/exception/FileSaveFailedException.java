package com.example.tlover.infra.file.exception;
import com.example.tlover.infra.file.constant.FileConstants.EFileExceptionMessage;

public class FileSaveFailedException extends IllegalArgumentException {
    public FileSaveFailedException() {super(EFileExceptionMessage.eFileSaveFailedExceptionMessage.getValue());}
}
