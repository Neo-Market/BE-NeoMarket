package com.neo.neomarket.dto;

public class AuthResultDTO {
    private final String message;

    public AuthResultDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}