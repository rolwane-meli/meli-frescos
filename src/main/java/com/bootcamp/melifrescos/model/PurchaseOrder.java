package com.bootcamp.melifrescos.model;

import com.bootcamp.melifrescos.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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
}
