package com.bootcamp.melifrescos.dto;

import com.bootcamp.melifrescos.model.Warehouse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WarehouseResponseDTO {
    private Long id;
    private String name;
    private Long representativeId;

    public WarehouseResponseDTO(Warehouse warehouse) {
        this.id = warehouse.getId();
        this.name = warehouse.getName();
        this.representativeId = warehouse.getRepresentative().getId();
    }
}
