package com.bootcamp.melifrescos.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderRequest {

    private Long id;

    @NotNull(message = "O ID do comprador é obrigatório.")
    private Long buyerId;

    @NotNull(message = "ID do vendedor é obrigatório.")
    private Long batchId;

    @Valid
    @NotNull(message = "Adicione produto no carrinho.")
    private PurchaseProductDTO product;
}
