package com.bootcamp.melifrescos.exceptions;

public class InvalidSectorTypeException extends RuntimeException {
    private String message;

    public InvalidSectorTypeException(String message) {
        super(message);
    }
}
