package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.dto.PurchaseOrderRequest;
import com.bootcamp.melifrescos.dto.PurchaseOrderResponse;

import com.bootcamp.melifrescos.dto.PurchaseOrderProductDTO;

import java.util.List;

public interface IPurchaseOrderService {
    void updateStatusToFinished(Long id);
    PurchaseOrderResponse create(PurchaseOrderRequest purchaseOrder);
    List<PurchaseOrderProductDTO> getProductsByPurchaseOrder(Long id);
}
