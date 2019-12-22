package com.armin.rights.memberscore.errors;

public class FileStorageException extends ApiExceptions {
    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}