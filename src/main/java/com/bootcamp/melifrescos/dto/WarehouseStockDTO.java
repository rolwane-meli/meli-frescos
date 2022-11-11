package com.bootcamp.melifrescos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WarehouseStockDTO {
    private Long id;
    private String totalQuantity;
}
