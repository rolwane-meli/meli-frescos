package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.PurchaseOrderRequest;
import com.bootcamp.melifrescos.dto.PurchaseOrderResponse;
import com.bootcamp.melifrescos.dto.PurchaseProductDTO;
import com.bootcamp.melifrescos.exceptions.NoQuantityBatchProduct;
import com.bootcamp.melifrescos.interfaces.*;
import com.bootcamp.melifrescos.dto.BatchDTO;
import com.bootcamp.melifrescos.enums.OrderStatus;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.exceptions.PurchaseAlreadyFinishedException;
import com.bootcamp.melifrescos.model.Batch;
import com.bootcamp.melifrescos.model.Buyer;
import com.bootcamp.melifrescos.model.ProductPurchaseOrder;
import com.bootcamp.melifrescos.model.PurchaseOrder;
import com.bootcamp.melifrescos.repository.IPurchaseOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.beans.Transient;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService implements IPurchaseOrderService {
    private final IBuyerService buyerService;
    private final IPurchaseOrderRepo repo;
    private final IProductPurchaseOrderService productPurchaseOrderService;
    private final IBatchService batchService;

    /**
     * Método responsável por mudar o status da PurchaseOrder para FINISHED
     * @param id PurchaseOrder a ser finalizada
     */
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

    /**
     * Método responsável por criar carrinho e se add produtos caso carrinho ja exista
     * @param purchaseOrder recebe-se pelo body de acordo com PurchaseOrderRequest
     * @return PurchaseOrderResponse
     */
    @Override
    @Transactional
    public PurchaseOrderResponse create(PurchaseOrderRequest purchaseOrder) {
        Optional<Buyer> buyer = buyerService.getById(purchaseOrder.getBuyerId());
        Optional<Batch> batch = batchService.getById(purchaseOrder.getBatchId());
        if (batch.get().getProductQuantity() < purchaseOrder.getProductDTO().getQuantity()) {
            throw new NoQuantityBatchProduct("O estoque não possui a quantidade de produto desejado.");
        }
        PurchaseOrder purchaseOrder1 = new PurchaseOrder(purchaseOrder, buyer.get());
        repo.save(purchaseOrder1);

        productPurchaseOrderService.create(new ProductPurchaseOrder(purchaseOrder1,
                purchaseOrder.getProductDTO().getPrice(), purchaseOrder.getProductDTO().getQuantity(), batch.get().getProduct(), purchaseOrder.getBatchId()));
        List<ProductPurchaseOrder> productPurchaseOrder = productPurchaseOrderService.getAllProductPurchaseOrder(purchaseOrder1);
        BigDecimal finalPrice = calcTotalPrice(productPurchaseOrder);

        return new PurchaseOrderResponse(purchaseOrder1.getStatus(), finalPrice, productPurchaseOrder);
    }

    /**
     * Método reponsável por calcular o preço total dos produtos.
     * @param productPurchaseOrder (List)
     * @return valor BigDecimal do total da compra do carrinho
     */
    private static BigDecimal calcTotalPrice(List<ProductPurchaseOrder> productPurchaseOrder) {
        BigDecimal finalPrice = BigDecimal.ZERO;
        for (ProductPurchaseOrder product: productPurchaseOrder) {
            BigDecimal totalPrice = product.getProductPrice().multiply(BigDecimal.valueOf(product.getProductQuantity()));
            finalPrice = finalPrice.add(totalPrice);
        }
        return finalPrice;
    }

    public Optional<PurchaseOrder> getById(Long id) {
        return repo.findById(id);
    }

}
