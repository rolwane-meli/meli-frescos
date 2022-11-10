package com.bootcamp.melifrescos.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
    FROZEN("FROZEN"),
    FRESH("FRESH"),
    REFRIGERATED("REFRIGERATED");

    private String name;

    public static Type fromValue(String type) {
        return Type.valueOf(type.toUpperCase());
    }
}
