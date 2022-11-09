package com.bootcamp.melifrescos.model;

import com.bootcamp.melifrescos.enums.Type;
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
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "O nome é obrigatório.")
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "O tipo é obrigatório")
    private Type type;

    @ManyToOne
    @JoinColumn(name = "idSeller")
    @JsonIgnoreProperties("products")
    @NotNull(message = "O vendedor é obrigatório.")
    private Seller seller;

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties("product")
    private List<Batch> batches;
}
