package com.bootcamp.melifrescos.enums;

import com.bootcamp.melifrescos.exceptions.EnumNotFound;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
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
