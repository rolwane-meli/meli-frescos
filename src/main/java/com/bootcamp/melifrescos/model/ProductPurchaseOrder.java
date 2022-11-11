package com.bootcamp.melifrescos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ProductPurchaseOrders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductPurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal productPrice;

    @Column(nullable = false)
    private int productQuantity;

    @ManyToOne
    @JoinColumn(name = "idPurchaseOrder")
    @JsonIgnoreProperties("productPurchaseOrders")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "idProduct")
    @JsonIgnoreProperties("productPurchaseOrders")
    private Product product;
}
