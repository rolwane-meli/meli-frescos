package com.bootcamp.melifrescos.dto;

import com.bootcamp.melifrescos.model.Buyer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderDTO {

    @NotNull(message = "Data é obrigatório.")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDateTime date;

    private String status;

    @NotNull(message = "O ID do comprador é obrigatório.")
    private Long buyerId;

    @NotEmpty(message = "Adicione um produto na lista.")
    private List<PurchaseProductDTO> productDTOList;
}
