package com.bootcamp.melifrescos.service;

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

    @Override
    public void updateStatusToFinished(Long id) {

    }

    public Optional<PurchaseOrder> getById(Long id) {
        return repo.findById(id);
    }
}
