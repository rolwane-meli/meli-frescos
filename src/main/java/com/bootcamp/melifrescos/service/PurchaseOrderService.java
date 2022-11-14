package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.ProductListDTO;
import com.bootcamp.melifrescos.dto.PurchaseOrderDTO;
import com.bootcamp.melifrescos.dto.PurchaseOrderResponse;
import com.bootcamp.melifrescos.dto.PurchaseProductDTO;
import com.bootcamp.melifrescos.interfaces.*;
import com.bootcamp.melifrescos.dto.BatchDTO;
import com.bootcamp.melifrescos.enums.OrderStatus;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.exceptions.PurchaseAlreadyFinishedException;
import com.bootcamp.melifrescos.model.Buyer;
import com.bootcamp.melifrescos.model.Product;
import com.bootcamp.melifrescos.model.Batch;
import com.bootcamp.melifrescos.model.ProductPurchaseOrder;
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
    private final IProductPurchaseOrderService productPurchaseOrderService;
    private final IBatchService batchService;

    /**
     * Método responsável por mudar o status da PurchaseOrder para FINISHED
     * @param id PurchaseOrder a ser finalizada
     */
    private PurchaseOrderResponse response;

    @Override
    public void updateStatusToFinished(Long id) {
        Optional<PurchaseOrder> optionalPurchaseOrder = repo.findById(id);

        if (optionalPurchaseOrder.isEmpty()) {
            throw new NotFoundException("O carrinho informado não existe");
        }

        PurchaseOrder purchaseOrder = optionalPurchaseOrder.get();

        if (purchaseOrder.getStatus() == OrderStatus.FINISHED) {
            throw new PurchaseAlreadyFinishedException("O carrinho já está finalizado");
        }

        purchaseOrder.setStatus(OrderStatus.FINISHED);

        ProductPurchaseOrder productPurchaseOrder = productPurchaseOrderService.getByPurchaseOrderId(purchaseOrder.getId());

        Batch batch = batchService.getById(productPurchaseOrder.getBatchId()).orElse(null);

        batch.setProductQuantity(batch.getProductQuantity() - productPurchaseOrder.getProductQuantity());

        BatchDTO batchDTO = new BatchDTO(
                batch.getId(),
                batch.getProduct().getId(),
                batch.getCurrentTemperature(),
                batch.getProductQuantity(),
                batch.getManufacturingDate(),
                batch.getManufacturingTime(),
                batch.getVolume(),
                batch.getDueDate(),
                batch.getPrice()
        );

        batchService.create(batchDTO);
        repo.save(purchaseOrder);
    }

    @Override
    public PurchaseOrderResponse create(PurchaseOrderDTO purchaseOrder) {
        buyerService.getById(purchaseOrder.getBuyerId());
        batchService.getById(purchaseOrder.getBatchId());

        //verificando se produto existe no lote informado
       purchaseOrder.getProductDTOList().forEach(p -> productService.findProductByBatchesById(purchaseOrder.getBatchId()));

        System.out.println();
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
