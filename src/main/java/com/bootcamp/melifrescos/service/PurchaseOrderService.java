package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.PurchaseOrderProductDTO;
import com.bootcamp.melifrescos.enums.OrderStatus;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.exceptions.PurchaseAlreadyFinishedException;
import com.bootcamp.melifrescos.interfaces.IPurchaseOrderService;
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

    /**
     * Método responsável por mudar o status da PurchaseOrder para FINISHED
     * @param id PurchaseOrder a ser finalizada
     */
    @Override
    public void updateStatusToFinished(Long id) {
        Optional<PurchaseOrder> optionalPurchaseOrder = repo.findById(id);

        isPurchaseOrderValid(optionalPurchaseOrder);

        PurchaseOrder purchaseOrder = optionalPurchaseOrder.get();

        purchaseOrder.setStatus(OrderStatus.FINISHED);

        repo.save(purchaseOrder);
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

        isPurchaseOrderValid(optionalPurchaseOrder);

        return repo.findProductsByPurchaseOrder(id);
    }


    private void isPurchaseOrderValid(Optional<PurchaseOrder> purchaseOrderOptional) {
        if (purchaseOrderOptional.isEmpty()) {
            throw new NotFoundException("O carrinho informado não existe");
        }
        PurchaseOrder purchaseOrder = purchaseOrderOptional.get();

        if (purchaseOrder.getStatus() == OrderStatus.FINISHED) {
            throw new PurchaseAlreadyFinishedException("O carrinho já está finalizado");
        }
    }
}
