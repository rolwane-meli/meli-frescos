package com.bootcamp.melifrescos.dto;

import com.bootcamp.melifrescos.model.Batch;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class InboundOrderDTO {
    private Long sectionCode;
    private LocalDateTime orderDate;
    private List<Batch> batchStock;
}
