package com.armin.rights.memberscore.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ApiFileNotFoundException extends FileStorageException {
    public ApiFileNotFoundException(String message) {
        super(message);
    }

    public ApiFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}