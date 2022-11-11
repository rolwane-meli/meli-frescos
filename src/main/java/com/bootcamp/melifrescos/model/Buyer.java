package com.bootcamp.melifrescos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "buyers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Buyer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 14, nullable = false, unique = true)
    private String cpf;

    @Column(length = 45, nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "buyer")
    @JsonIgnoreProperties("buyer")
    private List<PurchaseOrder> purchaseOrders;
}
