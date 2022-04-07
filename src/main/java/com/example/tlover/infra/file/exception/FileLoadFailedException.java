package com.example.tlover.infra.file.exception;
import com.example.tlover.infra.file.constant.FileConstants.EFileExceptionMessage;

public class FileLoadFailedException extends IllegalArgumentException {
    public FileLoadFailedException() {super(EFileExceptionMessage.eFileLoadFailedExceptionMessage.getValue());}
}
