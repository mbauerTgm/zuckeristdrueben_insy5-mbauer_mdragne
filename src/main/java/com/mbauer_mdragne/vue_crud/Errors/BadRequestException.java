package com.mbauer_mdragne.vue_crud.Errors;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}