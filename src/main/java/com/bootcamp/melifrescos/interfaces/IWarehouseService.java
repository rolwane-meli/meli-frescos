package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.dto.ProductStockDTO;
import com.bootcamp.melifrescos.dto.WarehouseRequestDTO;
import com.bootcamp.melifrescos.model.Warehouse;

import java.util.Optional;

public interface IWarehouseService {
    Warehouse create(WarehouseRequestDTO warehouseRequestDTO);
    Optional<Warehouse> getById(Long id);

    ProductStockDTO checkProductStock(Long productId);
}
