package com.bootcamp.melifrescos.repository;

import com.bootcamp.melifrescos.model.ProductPurchaseOrder;
import com.bootcamp.melifrescos.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductPurchaseOrderRepo extends JpaRepository<ProductPurchaseOrder, Long> {
    ProductPurchaseOrder findProductPurchaseOrderByPurchaseOrder(PurchaseOrder purchaseOrder);
    List<ProductPurchaseOrder> findAllProductPurchaseOrderByPurchaseOrder(PurchaseOrder purchaseOrder);
}
