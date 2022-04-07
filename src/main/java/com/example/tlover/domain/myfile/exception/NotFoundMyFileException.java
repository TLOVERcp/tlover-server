package com.example.tlover.domain.myfile.exception;

import com.example.tlover.domain.myfile.constant.MyFileConstants.EMyFileExceptionMessage;

public class NotFoundMyFileException extends IllegalArgumentException{
    public NotFoundMyFileException() {
        super(EMyFileExceptionMessage.eNotFoundMyFileExceptionMessage.getMessage());
    }
}
