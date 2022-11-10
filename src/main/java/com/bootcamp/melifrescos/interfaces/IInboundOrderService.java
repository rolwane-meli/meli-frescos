package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.dto.InboundOrderDTO;
import com.bootcamp.melifrescos.model.InboundOrder;

public interface IInboundOrderService {
    InboundOrder create(InboundOrderDTO inboundOrder);
    InboundOrder update(Long id, InboundOrderDTO inboundOrder);
}
