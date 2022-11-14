package com.bootcamp.melifrescos.dto;

import com.bootcamp.melifrescos.model.Batch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BatchListDTO {
    private Long batchNumber;
    private int currentQuantity;
    private LocalDateTime dueDate;
    private BigDecimal price;

    private Long sector;
    private Long warehouse;

    public BatchListDTO(Batch batch) {
        this.batchNumber = batch.getId();
        this.currentQuantity = batch.getProductQuantity();
        this.dueDate = batch.getDueDate();
        this.price = batch.getPrice();
        this.sector = batch.getInboundOrder().getSector().getId();
        this.warehouse = batch.getInboundOrder().getSector().getWarehouse().getId();
    }
}
