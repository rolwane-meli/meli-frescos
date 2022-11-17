package com.bootcamp.melifrescos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class WarehouseRequestDTO {

    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @NotNull(message = "O representante é obrigatório.")
    private Long representativeId;
}
