package com.bootcamp.melifrescos.dto;

import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.util.ValueOfEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class SectorRequestDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @NotNull(message = "A capacidade do setor deve ser informada.")
    private double capacity;

    @NotNull(message = "O tipo é obrigatório")
    @ValueOfEnum(enumClass = Type.class, message = "Tipo não identificado")
    private String type;

    @NotNull(message = "O armazém é obrigatório.")
    private Long warehouseId;
}
