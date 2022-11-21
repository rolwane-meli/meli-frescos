package com.bootcamp.melifrescos.dto;

import com.bootcamp.melifrescos.model.Product;
import lombok.Getter;
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
