package com.example.tlover.domain.user_thema.exception;

import com.example.tlover.domain.user.exception.UserExceptionMessage;

public class NotFoundUserThemaException extends RuntimeException{
    public NotFoundUserThemaException() {
        super(UserThemaExceptionMessage.NOT_FOUND_USER_THEMA_EXCEPTION_MESSAGE.getMessage());
    }
}
