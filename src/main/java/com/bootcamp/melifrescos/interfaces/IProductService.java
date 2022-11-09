package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.dto.ProductRequestDTO;
import com.bootcamp.melifrescos.model.Product;

public interface IProductService {
    public Product create(ProductRequestDTO product);
}
