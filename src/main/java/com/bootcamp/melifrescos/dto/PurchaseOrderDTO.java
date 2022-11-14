package com.bootcamp.melifrescos.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderDTO {

    @NotNull(message = "Data é obrigatório.")
 //   @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;

    @NotNull(message = "O ID do comprador é obrigatório.")
    private Long buyerId;

    @NotNull(message = "ID do vendedor é obrigatório.")
    private Long batchId;

    @NotEmpty(message = "Adicione um produto na lista.")
    private List<PurchaseProductDTO> productDTOList;
}
