package com.bootcamp.melifrescos.repository;

import com.bootcamp.melifrescos.dto.WarehouseStockDTO;
import com.bootcamp.melifrescos.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IWarehouseRepo extends JpaRepository<Warehouse, Long> {

    @Query("select new com.bootcamp.melifrescos.dto.WarehouseStockDTO( w.id, cast(SUM(b.productQuantity) as string))" +
            "from Product p " +
            "join Batch b on (p.id=b.product.id) " +
            "join InboundOrder ib on b.inboundOrder.id=ib.id " +
            "join Sector s on (ib.sector.id=s.id) " +
            "join Warehouse w on (s.warehouse.id=w.id) " +
            "where p.id=:productId " +
            "group by w.id"
    )
    List<WarehouseStockDTO> findTotalQuantityOfProductInWarehouse(@Param("productId")Long productId);
}
