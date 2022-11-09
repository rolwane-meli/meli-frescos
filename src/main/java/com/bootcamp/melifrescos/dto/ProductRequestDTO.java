package com.bootcamp.melifrescos.dto;

import com.bootcamp.melifrescos.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductRequestDTO {
    private String name;
    private Type type;
    private Long sellerId;
}
