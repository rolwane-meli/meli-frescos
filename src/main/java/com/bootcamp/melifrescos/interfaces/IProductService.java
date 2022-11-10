package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.dto.ProductRequestDTO;
import com.bootcamp.melifrescos.model.Product;

import java.util.Optional;

public interface IProductService {
    public Product create(ProductRequestDTO product);

    Optional<Product> getById(Long id);
}
