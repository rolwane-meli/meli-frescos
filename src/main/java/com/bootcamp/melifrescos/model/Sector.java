package com.bootcamp.melifrescos.model;

import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.util.ValueOfEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "sectors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @Column(nullable = false)
    @NotNull(message = "A capacidade é obrigatória.")
    private double capacity;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O tipo é obrigatório.")
    @ValueOfEnum(enumClass = Type.class, message = "Tipo não identificado")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "idWarehouse")
    @JsonIgnoreProperties("sectors")
    @NotNull(message = "O armazém é obrigatório.")
    private Warehouse warehouse;

    @OneToMany(mappedBy = "sector")
    @JsonIgnoreProperties("sector")
    private List<InboundOrder> inboundOrders;
}
