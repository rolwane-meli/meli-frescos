package com.bootcamp.melifrescos.exceptions;

public class EnumNotFound extends RuntimeException {
    public EnumNotFound(String message) {
        super(message);
    }
}
