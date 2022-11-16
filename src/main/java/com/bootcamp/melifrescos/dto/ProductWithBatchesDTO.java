package com.bootcamp.melifrescos.dto;

import com.bootcamp.melifrescos.model.Batch;
import com.bootcamp.melifrescos.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductWithBatchesDTO {
    private Long id;

    private String name;

    private List<BatchListDTO> batchStock;

    public ProductWithBatchesDTO(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.batchStock  = setBatchListDTOS(product.getBatches());
    }

    private List<BatchListDTO> setBatchListDTOS(List<Batch> batches){
        List<BatchListDTO> batchListDTOS = new ArrayList<>();
        batches.stream().forEach(batch -> {
            batchListDTOS.add(new BatchListDTO(batch));
        });

        return batchListDTOS;
    }
}
