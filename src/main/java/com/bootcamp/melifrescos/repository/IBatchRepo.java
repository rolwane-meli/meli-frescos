package com.bootcamp.melifrescos.repository;

import com.bootcamp.melifrescos.dto.BatchDTO;
import com.bootcamp.melifrescos.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface IBatchRepo extends JpaRepository<Batch, Long> {
    @Query("select " +
            "new com.bootcamp.melifrescos.dto.BatchDTO(b.id, b.product.id, b.currentTemperature, b.productQuantity, b.manufacturingDate, b.manufacturingTime, b.volume, b.dueDate, b.price)" +
            "from Batch b " +
            "join InboundOrder ib on (b.inboundOrder.id=ib.id) " +
            "where ib.sector.id=:sectorId " +
            "and b.dueDate between current_date() and :dueDate " +
            "order by b.dueDate")
    List<BatchDTO> findBatchesBySectorAndDurDate(@Param("dueDate") LocalDateTime dueDate, @Param("sectorId") Long sectorId);
}
