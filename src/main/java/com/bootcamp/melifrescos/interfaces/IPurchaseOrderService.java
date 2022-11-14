package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.dto.PurchaseOrderRequest;
import com.bootcamp.melifrescos.dto.PurchaseOrderResponse;

public interface IPurchaseOrderService {
    void updateStatusToFinished(Long id);
    PurchaseOrderResponse create(PurchaseOrderRequest purchaseOrder);
}
