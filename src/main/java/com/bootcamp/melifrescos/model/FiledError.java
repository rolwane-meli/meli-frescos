package com.bootcamp.melifrescos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FiledError {
    private String message;

    public FiledError(String message) {
        this.message = message;
    }
}
