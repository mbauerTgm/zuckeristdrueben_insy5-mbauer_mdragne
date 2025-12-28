package com.mbauer_mdragne.vue_crud;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}