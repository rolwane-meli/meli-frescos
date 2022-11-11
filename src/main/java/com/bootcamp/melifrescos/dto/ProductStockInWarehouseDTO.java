package com.bootcamp.melifrescos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductStockInWarehouseDTO {
    private Long productId;
    private List<WarehouseStockDTO> warehouses;
}
