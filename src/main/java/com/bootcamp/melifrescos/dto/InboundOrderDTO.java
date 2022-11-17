package com.bootcamp.melifrescos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderDTO {

    @NotNull(message = "O sertor é obrigatório.")
    private Long sectionCode;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "O dia da ordem é obrigatório.")
    private LocalDate orderDate;

    @Valid
    @NotEmpty(message = "Adicione o lote.")
    private List<BatchDTO> batchStock;
}
