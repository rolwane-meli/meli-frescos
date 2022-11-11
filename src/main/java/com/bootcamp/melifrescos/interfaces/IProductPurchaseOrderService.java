package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.model.ProductPurchaseOrder;

public interface IProductPurchaseOrderService {
    ProductPurchaseOrder getByPurchaseOrderId(Long id);
}
