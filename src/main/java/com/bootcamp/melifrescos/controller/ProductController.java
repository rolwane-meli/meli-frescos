package com.bootcamp.melifrescos.controller;

import com.bootcamp.melifrescos.dto.ProductRequestDTO;
import com.bootcamp.melifrescos.dto.ProductResponseDTO;
import com.bootcamp.melifrescos.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/product")
public class ProductController {

    @Autowired
    private IProductService service;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody @Valid ProductRequestDTO product){
        ProductResponseDTO newProduct = new ProductResponseDTO(service.create(product));
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll(){
        List<ProductResponseDTO> response = service.findProductsByBatches();
        if(response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ProductResponseDTO>> getAllByType(@PathVariable String type){
        List<ProductResponseDTO> response = service.findProductsByBatchesAndType(Type.fromValue(type.toUpperCase()));
        if(response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
