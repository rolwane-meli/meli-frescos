package com.bootcamp.melifrescos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseProductDTO {

    @NotNull(message = "ID do produto é obrigatório.")
    @Min(value = 1, message = "ID do produto não pode ser zero ou negativo.")
    private Long productId;

    @DecimalMin(value = "1", message = "Quantidade minima para compra é 1.")
    private BigDecimal quantity;
}
