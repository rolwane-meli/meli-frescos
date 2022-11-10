package com.bootcamp.melifrescos.exceptions;

public class UnavailableVolumeException extends RuntimeException{
    private String message;

    public UnavailableVolumeException(String message) {
        super(message);
    }
}
