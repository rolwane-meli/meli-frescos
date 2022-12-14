package com.bootcamp.melifrescos.enums;

import com.bootcamp.melifrescos.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
    FROZEN("FROZEN", "FF"),
    FRESH("FRESH", "FS"),
    REFRIGERATED("REFRIGERATED", "RF");

    private String name;
    private String sigla;

    public static Type fromValue(String type) {
        return Type.valueOf(type);
    }

    public static Type fromSigla(String sigla) {
        for (final Type t : Type.values()) {
            if (t.sigla.equalsIgnoreCase(sigla)) {
                return t;
            }
        }

        throw new NotFoundException("Tipo Invalido");
    }
}
