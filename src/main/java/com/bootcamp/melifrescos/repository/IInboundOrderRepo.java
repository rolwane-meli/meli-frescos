package com.bootcamp.melifrescos.repository;

import com.bootcamp.melifrescos.model.InboundOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInboundOrderRepo extends JpaRepository<InboundOrder, Long> {
}
