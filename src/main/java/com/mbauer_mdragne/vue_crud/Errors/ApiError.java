package com.mbauer_mdragne.vue_crud.Errors;

import java.time.Instant;

import org.springframework.http.HttpStatus;

public class ApiError {

    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final Instant timestamp = Instant.now();

    public ApiError(HttpStatus status, String message, String path) {
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
}