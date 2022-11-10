package com.bootcamp.melifrescos.exceptions;

public class SectorAlreadyExistsException extends RuntimeException{
    public SectorAlreadyExistsException(String message) {
        super(message);
    }
}
