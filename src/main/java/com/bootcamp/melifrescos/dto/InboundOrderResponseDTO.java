package com.bootcamp.melifrescos.dto;

import com.bootcamp.melifrescos.model.Batch;
import com.bootcamp.melifrescos.model.InboundOrder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InboundOrderResponseDTO {
    private Long id;
    private LocalDate orderDate;

    @JsonIgnoreProperties({"inboundOrder", "product"})
    private List<Batch> batches;

    public InboundOrderResponseDTO(InboundOrder inboundOrder) {
        this.id = inboundOrder.getId();
        this.orderDate = inboundOrder.getOrderDate();
        this.batches = inboundOrder.getBatches();
    }
}
