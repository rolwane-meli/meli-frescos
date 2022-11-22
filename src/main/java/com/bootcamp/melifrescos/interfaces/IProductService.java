package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.dto.*;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    public Product create(ProductRequestDTO product);

    Optional<Product> getById(Long id);

    List<ProductListDTO> findProductsByBatches();
    List<ProductListDTO> findProductsByBatchesAndType(Type type);

    ProductWithBatchesDTO getByIdWithBatches(Long idProduct);

    ProductWithBatchesDTO getByIdWithSortedBatches(Long idProduct,String type);

    List<ProductConvertedDTO> getAllProductsWithConvertedPrice(String currency);

    List<ProductConvertedDTO> getByProductIdWithConvertedPrice(Long id, String price);
}
