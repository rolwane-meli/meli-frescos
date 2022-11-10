package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.dto.InboundOrderDTO;
import com.bootcamp.melifrescos.model.InboundOrder;

public interface IInboundOrderService {
    public InboundOrder create(InboundOrderDTO inboundOrder);
}
