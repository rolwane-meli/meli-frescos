package com.bootcamp.melifrescos.dto;

import com.bootcamp.melifrescos.model.Batch;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderDTO {
    private Long sectionCode;
    private LocalDateTime orderDate;
    private List<BatchDTO> batchStock;
}
