package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.enums.OrderStatus;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.exceptions.PurchaseAlreadyFinishedException;
import com.bootcamp.melifrescos.interfaces.IPurchaseOrderService;
import com.bootcamp.melifrescos.model.PurchaseOrder;
import com.bootcamp.melifrescos.repository.IPurchaseOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PurchaseOrderService implements IPurchaseOrderService {

    private final IPurchaseOrderRepo repo;

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

        repo.save(purchaseOrder);
    }
}
