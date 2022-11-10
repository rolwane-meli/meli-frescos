package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.dto.WarehouseRequestDTO;
import com.bootcamp.melifrescos.model.Warehouse;

public interface IWarehouseService {
    Warehouse create(WarehouseRequestDTO warehouseRequestDTO);
}
