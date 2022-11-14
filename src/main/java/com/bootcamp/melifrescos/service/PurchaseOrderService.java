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
        // Procura a purchaseOrder a ser finalizada
        PurchaseOrder purchaseOrder = repo.findById(id).orElse(null);

        if (purchaseOrder == null) {
            throw new NotFoundException("O carrinho de id: " + id + " não existe");
        }

        if (purchaseOrder.getStatus() == OrderStatus.FINISHED) {
            throw new PurchaseAlreadyFinishedException("O carrinho já está finalizado");
        }

        // busca na tabela intermediária 'ProductPurchaseOrder' todos os registros com a referida purchaseOrder
        List<ProductPurchaseOrder> productPurchaseOrders = productPurchaseOrderService.getAllByPurchaseOrder(purchaseOrder);

        productPurchaseOrders.forEach((productPurchaseOrder) -> {
            // busca o lote que está no registro de 'ProductPurchaseOrder'
            Batch batch = batchService.getById(productPurchaseOrder.getBatchId()).orElse(null);

            // atualiza a quantidade de produtos dispiníveis no lote
            batch.setProductQuantity(batch.getProductQuantity() - productPurchaseOrder.getProductQuantity());

            // se o estoque estiver esgotado então zera o volume ocupado pelo lote
            if (batch.getProductQuantity() == 0) {
                batch.setVolume(0);
            }

            // monta o batchDTO para fazer a inserção no banco
            BatchDTO batchDTO = this.mountBatchDTO(batch);

            batchService.create(batchDTO);
        });

        // muda o status da ordem de compra para FINISHED
        purchaseOrder.setStatus(OrderStatus.FINISHED);

        repo.save(purchaseOrder);
    }

    /**
     * Método responsável por converter um Batch em um batchDTO
     * @param batch
     * @return batchDTO
     */
    private BatchDTO mountBatchDTO(Batch batch) {
        return new BatchDTO(
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
    }

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

        List<ProductPurchaseOrder> productPurchaseOrder = productPurchaseOrderService.getAllByPurchaseOrder(purchaseOrder1);
        BigDecimal finalPrice = calcTotalPrice(productPurchaseOrder);


        return new PurchaseOrderResponse(purchaseOrder1.getStatus(), finalPrice, productPurchaseOrder);
    }

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
