package com.bootcamp.melifrescos.controller;

import com.bootcamp.melifrescos.dto.WarehouseRequestDTO;
import com.bootcamp.melifrescos.dto.WarehouseResponseDTO;
import com.bootcamp.melifrescos.interfaces.IRepresentativeService;
import com.bootcamp.melifrescos.interfaces.IWarehouseService;
import com.bootcamp.melifrescos.model.Representative;
import com.bootcamp.melifrescos.model.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fresh-products/warehouse")
public class WarehouseController {

    @Autowired
    private IWarehouseService service;

    @PostMapping
    public ResponseEntity<WarehouseResponseDTO> create(@RequestBody WarehouseRequestDTO warehouseRequestDTO) {
        WarehouseResponseDTO warehouseResponseDTO = new WarehouseResponseDTO(service.create(warehouseRequestDTO));
        return new ResponseEntity<>(warehouseResponseDTO, HttpStatus.CREATED);
    }
}
