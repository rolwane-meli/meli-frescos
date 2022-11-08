package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.interfaces.IInboundOrderService;
import com.bootcamp.melifrescos.model.InboundOrder;
import com.bootcamp.melifrescos.repository.IInboundOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InboundOrderService implements IInboundOrderService {

    private final IInboundOrderRepo repo;

    @Override
    public InboundOrder create(InboundOrder inboundOrder) {
        return repo.save(inboundOrder);
    }
}
