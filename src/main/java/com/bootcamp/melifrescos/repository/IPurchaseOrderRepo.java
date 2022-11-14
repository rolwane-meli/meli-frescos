package com.bootcamp.melifrescos.repository;

import com.bootcamp.melifrescos.dto.PurchaseOrderProductDTO;
import com.bootcamp.melifrescos.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPurchaseOrderRepo extends JpaRepository<PurchaseOrder, Long> {
    @Query("select new com.bootcamp.melifrescos.dto.PurchaseOrderProductDTO(p.id, p.name, ppo.productPrice, ppo.productQuantity) " +
            "from Product as p " +
            "inner join ProductPurchaseOrder as ppo on (ppo.product.id = p.id) " +
            "where ppo.purchaseOrder.id = :id")
    List<PurchaseOrderProductDTO> findProductsByPurchaseOrder(@Param("id") Long id);
}
