package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.BatchDTO;
import com.bootcamp.melifrescos.enums.OrderStatus;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.exceptions.PurchaseAlreadyFinishedException;
import com.bootcamp.melifrescos.interfaces.IBatchService;
import com.bootcamp.melifrescos.interfaces.IProductPurchaseOrderService;
import com.bootcamp.melifrescos.interfaces.IPurchaseOrderService;
import com.bootcamp.melifrescos.model.Batch;
import com.bootcamp.melifrescos.model.ProductPurchaseOrder;
import com.bootcamp.melifrescos.model.PurchaseOrder;
import com.bootcamp.melifrescos.repository.IPurchaseOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService implements IPurchaseOrderService {

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

        // busca na tabela intermediária 'ProductPurchaseOrder' um registro com a referida purchaseOrder
        ProductPurchaseOrder productPurchaseOrder = productPurchaseOrderService.getByPurchaseOrder(purchaseOrder);

        // busca o lote que está no registro de 'ProductPurchaseOrder'
        Batch batch = batchService.getById(productPurchaseOrder.getBatchId()).orElse(null);

        // muda o status da ordem de compra para FINISHED
        purchaseOrder.setStatus(OrderStatus.FINISHED);

        // atualiza a quantidade de produtos dispiníveis no lote
        batch.setProductQuantity(batch.getProductQuantity() - productPurchaseOrder.getProductQuantity());

        // se o estoque estiver esgotado então zera o volume ocupado pelo lote
        if (batch.getProductQuantity() == 0) {
            batch.setVolume(0);
        }

        // monta o batchDTO para fazer a inserção no banco
        BatchDTO batchDTO = this.mountBatchDTO(batch);

        batchService.create(batchDTO);
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
}
