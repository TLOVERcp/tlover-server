package com.example.tlover.infra.file.exception;


import com.example.tlover.infra.file.constant.FileConstants.EFileExceptionMessage;

public class FileExtensionException extends IllegalArgumentException {
    public FileExtensionException() {super(EFileExceptionMessage.eFileExtensionExceptionMessage.getValue());}
}
