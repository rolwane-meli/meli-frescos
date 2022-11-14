package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.model.ProductPurchaseOrder;
import com.bootcamp.melifrescos.model.PurchaseOrder;

import java.util.List;

public interface IProductPurchaseOrderService {
    ProductPurchaseOrder getByPurchaseOrderId(Long id);
    ProductPurchaseOrder create(ProductPurchaseOrder productPurchaseOrder);
    List<ProductPurchaseOrder> getAllProductPurchaseOrder(PurchaseOrder purchaseOrder);
}
