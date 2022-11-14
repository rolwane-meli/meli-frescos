package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.dto.ProductListDTO;
import com.bootcamp.melifrescos.dto.ProductRequestDTO;
import com.bootcamp.melifrescos.dto.PurchaseOrderResponse;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    public Product create(ProductRequestDTO product);

    Optional<Product> getById(Long id);

    List<ProductListDTO> findProductsByBatches();
    List<ProductListDTO> findProductsByBatchesAndType(Type type);
}
