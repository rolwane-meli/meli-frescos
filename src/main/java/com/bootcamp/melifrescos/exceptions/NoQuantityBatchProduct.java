package com.bootcamp.melifrescos.exceptions;

public class NoQuantityBatchProduct extends RuntimeException {
    public NoQuantityBatchProduct(String message) {
        super(message);
    }
}
