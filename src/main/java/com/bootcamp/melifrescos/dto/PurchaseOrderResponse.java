package com.bootcamp.melifrescos.dto;

import com.bootcamp.melifrescos.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseOrderResponse {
    private OrderStatus status;
    private BigDecimal totalPrice;
}
