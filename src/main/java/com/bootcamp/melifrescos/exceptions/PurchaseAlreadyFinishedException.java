package com.bootcamp.melifrescos.exceptions;

public class PurchaseAlreadyFinishedException extends RuntimeException {
    public PurchaseAlreadyFinishedException(String message) {
        super(message);
    }
}
