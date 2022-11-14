package com.bootcamp.melifrescos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class ProductPurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal productPrice;

    @Column(nullable = false)
    private int productQuantity;

    @Column(nullable = false)
    private Long batchId;

    @ManyToOne
    @JoinColumn(name = "idPurchaseOrder")
    @JsonIgnoreProperties("productPurchaseOrders")
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name = "idProduct")
    @JsonIgnoreProperties("productPurchaseOrders")
    private Product product;

    public ProductPurchaseOrder(PurchaseOrder purchaseOrder, BigDecimal productPrice, int productQuantity, Product product, Long batchId){
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.purchaseOrder = purchaseOrder;
        this.batchId = batchId;
        this.product = product;
    }
    public ProductPurchaseOrder(Long id, BigDecimal productPrice, int productQuantity, Long batchId, PurchaseOrder purchaseOrder, Product product) {
        this.id = id;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.batchId = batchId;
        this.purchaseOrder = purchaseOrder;
        this.product = product;
    }
}
