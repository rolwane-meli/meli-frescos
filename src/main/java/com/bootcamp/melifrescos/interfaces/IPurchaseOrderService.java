package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.dto.PurchaseOrderDTO;
import com.bootcamp.melifrescos.dto.PurchaseOrderResponse;

public interface IPurchaseOrderService {
    void updateStatusToFinished(Long id);
    PurchaseOrderResponse create(PurchaseOrderDTO purchaseOrder);
}
