package com.ATM.application.command;

public record AtmResponse(String message) {
    public static AtmResponse ok(String message) {
        return new AtmResponse(message);
    }
}
