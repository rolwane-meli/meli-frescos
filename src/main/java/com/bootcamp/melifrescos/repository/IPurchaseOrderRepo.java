package com.bootcamp.melifrescos.repository;

import com.bootcamp.melifrescos.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchaseOrderRepo extends JpaRepository<PurchaseOrder, Long> {
}
