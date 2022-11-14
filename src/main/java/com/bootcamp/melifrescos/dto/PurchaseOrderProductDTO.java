package com.bootcamp.melifrescos.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PurchaseOrderProductDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private int productQuantity;
}