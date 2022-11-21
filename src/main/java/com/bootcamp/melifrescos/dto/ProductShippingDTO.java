package com.bootcamp.melifrescos.dto;

import com.bootcamp.melifrescos.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ProductShippingDTO {
    private Long id;
    private String name;
    private int quantity;

    public ProductShippingDTO(Product product, int quantity) {
        this.id = product.getId();
        this.name = product.getName();
        this.quantity = quantity;
    }
}
