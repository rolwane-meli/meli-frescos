package com.bootcamp.melifrescos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inboundOrders")
@Getter
@Setter
@NoArgsConstructor
public class InboundOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 11, nullable = false)
    private int order_number;

    @Column(nullable = false)
    private LocalDateTime order_date;

    @ManyToOne
    @JoinColumn(name = "idSector")
    @JsonIgnoreProperties("inboundOrders")
    private Sector sector;
}
