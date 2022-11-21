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

    /**
     * Método responsável por buscar ProductPurchaseOrder
     * @param purchaseOrder
     * @return Product purchase order
     */
    @Override
    public ProductPurchaseOrder getByPurchaseOrder(PurchaseOrder purchaseOrder) {
        return repo.findProductPurchaseOrderByPurchaseOrder(purchaseOrder);
    }

    /**
     * Método responsável por Criar Product Purchase Order
     * @param productPurchaseOrder via body
     * @return Product Purchase Order
     */
    @Override
    public ProductPurchaseOrder create(ProductPurchaseOrder productPurchaseOrder) {
        return repo.save(productPurchaseOrder);
    }

    /**
     * Método responsável por Buscar uma lista de ProductPurchaseOrder
     * @param purchaseOrder
     * @return List<ProductPurchaseOrder>
     */
    @Override
    public List<ProductPurchaseOrder> getAllByPurchaseOrder(PurchaseOrder purchaseOrder) {
        return repo.findAllProductPurchaseOrderByPurchaseOrder(purchaseOrder);
    }
}
