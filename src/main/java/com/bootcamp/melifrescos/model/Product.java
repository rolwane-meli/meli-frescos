package com.bootcamp.melifrescos.model;

import com.bootcamp.melifrescos.enums.Type;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "idSeller")
    @JsonIgnoreProperties("products")
    private Seller seller;

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties("product")
    private List<Batch> batches;

    @OneToMany(mappedBy = "product")
    @JsonIgnoreProperties("product")
    private List<ProductPurchaseOrder> productPurchaseOrders;

    public Product(Long id, String name, Type type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}