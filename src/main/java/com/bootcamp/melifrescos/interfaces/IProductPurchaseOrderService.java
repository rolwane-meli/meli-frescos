package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.model.ProductPurchaseOrder;
import com.bootcamp.melifrescos.model.PurchaseOrder;

import java.util.List;
import com.bootcamp.melifrescos.model.PurchaseOrder;

public interface IProductPurchaseOrderService {
    ProductPurchaseOrder create(ProductPurchaseOrder productPurchaseOrder);
    List<ProductPurchaseOrder> getAllProductPurchaseOrder(PurchaseOrder purchaseOrder);
    ProductPurchaseOrder getByPurchaseOrder(PurchaseOrder purchaseOrder);
}
