package com.bootcamp.melifrescos.dto;

import com.bootcamp.melifrescos.enums.Type;
import lombok.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductConvertedDTO {
    private Long id;
    private String name;
    private Type type;
    private BigDecimal price;
    private int productQuantity;
    private String currency;
    private Long idBatch;

    public ProductConvertedDTO(ProductListDTO productListDTO, String currency, BigDecimal price){
        this.setId(productListDTO.getId());
        this.setName(productListDTO.getName());
        this.setType(productListDTO.getType());
        this.setPrice(productListDTO.getPrice().multiply(price).setScale(new MathContext(2).getPrecision(),RoundingMode.HALF_EVEN));
        this.setProductQuantity(productListDTO.getProductQuantity());
        this.setCurrency(currency);
        this.setIdBatch(productListDTO.getIdBatch());
    }
}
