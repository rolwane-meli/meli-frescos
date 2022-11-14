package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.model.ProductPurchaseOrder;
import com.bootcamp.melifrescos.model.PurchaseOrder;

public interface IProductPurchaseOrderService {
    ProductPurchaseOrder getByPurchaseOrder(PurchaseOrder purchaseOrder);
}
