package com.bootcamp.melifrescos.dto;

import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private Type type;
    private Long sellerId;

    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.type = product.getType();
        this.sellerId = product.getSeller().getId();
    }
}

