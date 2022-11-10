package com.bootcamp.melifrescos.exceptions;

public class EnumNotFoundException extends RuntimeException {
    public EnumNotFoundException(String message) {
        super(message);
    }
}
