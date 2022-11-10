package com.bootcamp.melifrescos.dto;

import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.util.ValueOfEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String name;


    @NotNull(message = "O tipo é obrigatório")
    @ValueOfEnum(enumClass = Type.class, message = "Tipo não identificado")
    private String type;

    @NotNull(message = "O vendedor é obrigatório.")
    private Long sellerId;
}
