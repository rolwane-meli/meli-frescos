package com.bootcamp.melifrescos.controller;

import com.bootcamp.melifrescos.dto.BatchDTO;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.interfaces.IBatchService;
import com.bootcamp.melifrescos.model.Batch;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/batch")
public class BatchController {

    @Autowired
    private IBatchService service;

    @PostMapping
    public ResponseEntity<BatchDTO> create(@RequestBody @Valid BatchDTO batchDTO){
        Batch result = service.create(batchDTO);
        BatchDTO resultDto = new BatchDTO();
        BeanUtils.copyProperties(result, resultDto);
        resultDto.setProductId(result.getProduct().getId());
        return new ResponseEntity<>(resultDto, HttpStatus.CREATED);
    }

    @GetMapping
    public  ResponseEntity<List<BatchDTO>> getAllByDueDateAndCategory(@RequestParam int days,@RequestParam String type){
        return new ResponseEntity<>(service.getAllByDueDateAndCategory(days,Type.fromSigla(type)), HttpStatus.OK);
    }

    @GetMapping("/due-date")
    public ResponseEntity<List<BatchDTO>> getAllBatchesBySector(@RequestParam Long sectorId, @RequestParam int numberOfDays){
        return new ResponseEntity<>(service.getBatchesBySector(sectorId, numberOfDays), HttpStatus.OK);
    }
}
