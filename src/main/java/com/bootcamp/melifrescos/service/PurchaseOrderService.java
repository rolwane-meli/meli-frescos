package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.ProductListDTO;
import com.bootcamp.melifrescos.dto.PurchaseOrderDTO;
import com.bootcamp.melifrescos.dto.PurchaseOrderResponse;
import com.bootcamp.melifrescos.dto.PurchaseProductDTO;
import com.bootcamp.melifrescos.interfaces.IBuyerService;
import com.bootcamp.melifrescos.interfaces.IProductService;
import com.bootcamp.melifrescos.interfaces.IPurchaseOrderService;
import com.bootcamp.melifrescos.model.Buyer;
import com.bootcamp.melifrescos.model.Product;
import com.bootcamp.melifrescos.model.PurchaseOrder;
import com.bootcamp.melifrescos.repository.IPurchaseOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService implements IPurchaseOrderService {

    private final IProductService productService;
    private final IBuyerService buyerService;
    private final IPurchaseOrderRepo repo;

    private PurchaseOrderResponse response;

    @Override
    public void updateStatusToFinished(Long id) {

    }

    @Override
    public PurchaseOrderResponse create(PurchaseOrderDTO purchaseOrder) {
        Optional<Buyer> buyer = buyerService.getById(purchaseOrder.getBuyerId());
        purchaseOrder.getProductDTOList().forEach(p -> productService.getById(p.getProductId()));

        List<ProductListDTO> productsBuyer = productService.findProductsByBatches()

        BigDecimal totalPrice;

        for (PurchaseProductDTO product: purchaseOrder.getProductDTOList()) {
            totalPrice = product.getQuantity();
        }
        return new PurchaseOrderResponse();

    }

    public Optional<PurchaseOrder> getById(Long id) {
        return repo.findById(id);
    }
}
