package com.example.tlover.infra.file.exception;
import com.example.tlover.infra.file.constant.FileConstants.EFileExceptionMessage;

public class ImageNotFoundException extends IllegalArgumentException {
    public ImageNotFoundException() {super(EFileExceptionMessage.eImageNotFoundExceptionMessage.getValue());}
}
