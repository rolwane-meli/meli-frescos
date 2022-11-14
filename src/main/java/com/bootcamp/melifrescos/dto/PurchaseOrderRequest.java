package com.bootcamp.melifrescos.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderRequest {

    private Long id;

    @NotNull(message = "O ID do comprador é obrigatório.")
    private Long buyerId;

    @NotNull(message = "ID do vendedor é obrigatório.")
    private Long batchId;

    @NotNull(message = "Adicione produto no carrinho.")
    private PurchaseProductDTO productDTO;
}
