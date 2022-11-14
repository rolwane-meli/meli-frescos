package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.model.ProductPurchaseOrder;
import com.bootcamp.melifrescos.model.PurchaseOrder;
import com.bootcamp.melifrescos.model.PurchaseOrder;

import java.util.List;

public interface IProductPurchaseOrderService {
    ProductPurchaseOrder getByPurchaseOrder(PurchaseOrder purchaseOrder);
    ProductPurchaseOrder create(ProductPurchaseOrder productPurchaseOrder);
    List<ProductPurchaseOrder> getAllByPurchaseOrder(PurchaseOrder purchaseOrder);
}
