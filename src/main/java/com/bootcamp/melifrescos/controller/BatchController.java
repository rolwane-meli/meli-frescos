package com.bootcamp.melifrescos.controller;

import com.bootcamp.melifrescos.dto.BatchDTO;
import com.bootcamp.melifrescos.interfaces.IBatchService;
import com.bootcamp.melifrescos.model.Batch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/fresh-products/batch")
public class BatchController {

    @Autowired
    private IBatchService service;

    @PostMapping
    public ResponseEntity<BatchDTO> create(@RequestBody BatchDTO batchDTO){
        service.create(batchDTO);
        return new ResponseEntity<>(batchDTO, HttpStatus.CREATED);
    }
}
