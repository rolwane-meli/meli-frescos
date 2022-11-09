package com.bootcamp.melifrescos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FiledError {
    private String field;
    private String message;
    private String rejectedValue;

    public FiledError(String field, String message, String rejectedValue) {
        this.field = field;
        this.message = message;
        this.rejectedValue = rejectedValue;
    }
}
