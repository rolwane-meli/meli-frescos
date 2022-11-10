package com.bootcamp.melifrescos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BatchDTO {
    private Long id;
    private Long productId;
    private double currentTemperature;
    private int productQuantity;
    private LocalDate manufacturingDate;
    private LocalTime manufacturingTime;
    private double volume;
    private LocalDateTime dueDate;
    private BigDecimal price;
}