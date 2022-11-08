package com.bootcamp.melifrescos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "batches")
@Getter
@Setter
@NoArgsConstructor
public class Batch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double currentTemperature;

    @Column(length = 11, nullable = false)
    private int productQuantity;

    @Column(nullable = false)
    private LocalDate manufacturingDate;

    @Column(nullable = false)
    private LocalTime manufacturingTime;

    @Column(nullable = false)
    private double volume;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "idProduct")
    @JsonIgnoreProperties("batches")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "idInboundOrder")
    @JsonIgnoreProperties("batches")
    private InboundOrder inboundOrder;
}
