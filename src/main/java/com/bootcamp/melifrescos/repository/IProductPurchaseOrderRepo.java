package com.bootcamp.melifrescos.repository;

import com.bootcamp.melifrescos.model.ProductPurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductPurchaseOrderRepo extends JpaRepository<ProductPurchaseOrder, Long> {
    ProductPurchaseOrder findProductPurchaseOrderByPurchaseOrder(Long id);
}
