package com.bootcamp.melifrescos.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "batches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double currentTemperature;

    @Column(length = 11, nullable = false)
    @NotNull(message = "Quantidade é obrigatório.")
    private int productQuantity;

    @Column(nullable = false)
    @NotNull(message = "Data de Fabricação é obrigatório.")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate manufacturingDate;

    @Column(nullable = false)
    @JsonFormat(pattern="HH:mm:ss")
    @NotNull(message = "Tempo de Fabricação é obrigatório.")
    private LocalTime manufacturingTime;

    @Column(nullable = false)
    @NotNull(message = "O volume é obrigatório.")
    private double volume;

    @Column(nullable = false)
    @NotNull(message = "O vencimento é obrigatório.")
    private LocalDateTime dueDate;


    @Column(nullable = false)
    @NotNull(message = "O preço é obrigatório.")
    @DecimalMin(value = "0.1", message = "O preco precisa ser maior que 0.")
    private BigDecimal price;

    @ManyToOne
    @Valid
    @JoinColumn(name = "idProduct")
    @JsonIgnoreProperties("batches")
    @NotNull(message = "O produto é obrigatório.")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "idInboundOrder")
    @JsonIgnoreProperties("batches")
    private InboundOrder inboundOrder;
}
