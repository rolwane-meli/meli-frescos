package com.bootcamp.melifrescos.dto;

import com.bootcamp.melifrescos.enums.OrderStatus;
import com.bootcamp.melifrescos.model.ProductPurchaseOrder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderResponse {
    private OrderStatus status;
    private BigDecimal totalPrice;

    @JsonIgnoreProperties({"purchaseOrder", "product"})
    private List<ProductPurchaseOrder> products;
}
