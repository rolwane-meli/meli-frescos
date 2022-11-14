package com.bootcamp.melifrescos.model;

import com.bootcamp.melifrescos.dto.PurchaseOrderRequest;
import com.bootcamp.melifrescos.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "purchaseOrders")
@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @ManyToOne
    @JoinColumn(name = "idBuyer")
    @JsonIgnoreProperties("purchaseOrders")
    private Buyer buyer;

    @OneToMany(mappedBy = "purchaseOrder")
    @JsonIgnoreProperties("purchaseOrder")
    private List<ProductPurchaseOrder> productPurchaseOrders;

    public PurchaseOrder(PurchaseOrderRequest purchaseRequest, Buyer buyer) {
        this.id = purchaseRequest.getId();
        this.date = LocalDateTime.now();
        this.status = OrderStatus.OPEN;
        this.buyer = buyer;
    }

    public PurchaseOrder(Long id, LocalDateTime date, OrderStatus status, Buyer buyer) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.buyer = buyer;
    }
}
