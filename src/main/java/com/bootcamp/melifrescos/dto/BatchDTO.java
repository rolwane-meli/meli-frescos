package com.bootcamp.melifrescos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BatchDTO {
    private Long id;

    @NotNull(message = "O produto é obrigatório.")
    private Long productId;

    private double currentTemperature;

    @NotNull(message = "Quantidade é obrigatório.")
    private int productQuantity;

    @NotNull(message = "Data de Fabricação é obrigatório.")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @PastOrPresent(message = "A data de fabricaçao não pode ter uma data futura.")
    private LocalDate manufacturingDate;


    @NotNull(message = "Tempo de Fabricação é obrigatório.")
    @JsonFormat(pattern="HH:mm:ss")
    private LocalTime manufacturingTime;

    @NotNull(message = "O volume é obrigatório.")
    private double volume;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "A data de vencimento é obrigatório.")
    @FutureOrPresent(message = "Produto está vencido.")
    private LocalDate dueDate;

    @NotNull(message = "O preço é obrigatório.")
    @DecimalMin(value = "0.1", message = "O preco precisa ser maior que 0.")
    private BigDecimal price;
}