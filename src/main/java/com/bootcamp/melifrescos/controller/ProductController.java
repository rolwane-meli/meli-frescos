package com.bootcamp.melifrescos.controller;

import com.bootcamp.melifrescos.dto.ProductRequestDTO;
import com.bootcamp.melifrescos.dto.ProductResponseDTO;
import com.bootcamp.melifrescos.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}
