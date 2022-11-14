package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.dto.PurchaseOrderProductDTO;

import java.util.List;

public interface IPurchaseOrderService {
    void updateStatusToFinished(Long id);
    List<PurchaseOrderProductDTO> getProductsByPurchaseOrder(Long id);
}
