package com.bootcamp.melifrescos.dto;

import com.bootcamp.melifrescos.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductListDTO {
        private Long id;
        private String name;
        private Type type;
        private BigDecimal price;
        private int productQuantity;
        private Long idBatch;
}

