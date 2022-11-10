package com.bootcamp.melifrescos.exceptions;

public class InvalidEnumTypeException extends RuntimeException{
    private String message;

    public InvalidEnumTypeException(String message) {
        super(message);
    }
}
