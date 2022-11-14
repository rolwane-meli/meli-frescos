package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.interfaces.IProductPurchaseOrderService;
import com.bootcamp.melifrescos.model.ProductPurchaseOrder;
import com.bootcamp.melifrescos.model.PurchaseOrder;
import com.bootcamp.melifrescos.repository.IProductPurchaseOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductPurchaseOrderService implements IProductPurchaseOrderService {

    private final IProductPurchaseOrderRepo repo;

    @Override
    public ProductPurchaseOrder getByPurchaseOrder(PurchaseOrder purchaseOrder) {
        return repo.findProductPurchaseOrderByPurchaseOrder(purchaseOrder);
    }

    @Override
    public ProductPurchaseOrder create(ProductPurchaseOrder productPurchaseOrder) {
        return repo.save(productPurchaseOrder);
    }

    @Override
    public List<ProductPurchaseOrder> getAllProductPurchaseOrder(PurchaseOrder purchaseOrder) {
        return repo.findAllProductPurchaseOrderByPurchaseOrder(purchaseOrder);
    }
}
