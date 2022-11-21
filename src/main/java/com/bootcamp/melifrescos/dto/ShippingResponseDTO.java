package com.bootcamp.melifrescos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShippingResponseDTO {
    private String cep;
    private String UF;
    private String locality;
    private BigDecimal shippingPrice;
    private ProductShippingDTO product;
}
