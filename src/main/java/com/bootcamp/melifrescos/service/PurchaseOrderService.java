package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.BatchDTO;
import com.bootcamp.melifrescos.dto.PurchaseOrderProductDTO;
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

import java.util.List;
import java.util.Optional;

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

        // atualiza a quantidade de produtos disponíveis no lote
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
     * It takes a Batch object and returns a BatchDTO object
     *
     * @param batch the batch object that we want to convert to a DTO
     * @return A BatchDTO object.
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


    /**
     * "If the purchase order exists and is not finished, return the products in it."
     * The first thing we do is check if the purchase order exists. If it doesn't, we throw a NotFoundException
     *
     * @param id The id of the purchase order
     * @return A list of PurchaseOrderProductDTO
     */
    @Override
    public List<PurchaseOrderProductDTO> getProductsByPurchaseOrder(Long id) {
        Optional<PurchaseOrder> optionalPurchaseOrder = repo.findById(id);
        if (optionalPurchaseOrder.isEmpty()) {
            throw new NotFoundException("O carrinho informado não existe");
        }
        PurchaseOrder purchaseOrder = optionalPurchaseOrder.get();

        if (purchaseOrder.getStatus() == OrderStatus.FINISHED) {
            throw new PurchaseAlreadyFinishedException("O carrinho já está finalizado");
        }

        return repo.findProductsByPurchaseOrder(id);
    }
}
