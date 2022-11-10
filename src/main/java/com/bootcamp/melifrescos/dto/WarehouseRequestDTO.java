package com.bootcamp.melifrescos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WarehouseRequestDTO {
    private String name;
    private Long representativeId;
}
