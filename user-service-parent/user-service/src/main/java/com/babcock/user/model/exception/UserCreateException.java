package com.babcock.securityadmin.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserCreateException extends RuntimeException {

    public UserCreateException(String message) {
        super(message);
    }

    public UserCreateException(Throwable throwable) {
        super(throwable);
    }
}
