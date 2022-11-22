package com.bootcamp.melifrescos.controller;

import com.bootcamp.melifrescos.dto.*;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.interfaces.IProductService;
import com.bootcamp.melifrescos.model.Product;
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
    public ResponseEntity<List<ProductListDTO>> getAll(){
        List<ProductListDTO> response = service.findProductsByBatches();
        if(response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ProductListDTO>> getAllByType(@PathVariable String type){
        List<ProductListDTO> response = service.findProductsByBatchesAndType(Type.fromSigla(type));
        if(response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductWithBatchesDTO> getByIdWithBatches(@PathVariable Long id){
        return new ResponseEntity<>(service.getByIdWithBatches(id),HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", params = {"orderByType"})
    public ResponseEntity<ProductWithBatchesDTO> getByIdWithSortedBatches(@PathVariable Long id, @RequestParam String orderByType){
        return new ResponseEntity<>(service.getByIdWithSortedBatches(id,orderByType),HttpStatus.OK);
    }

    @GetMapping("/international-market")
    public ResponseEntity<List<ProductListBatchDTO>> getAllProductsWithConvertedPrice(@RequestParam String currency){
        return new ResponseEntity<>(service.getAllProductsWithConvertedPrice(currency),HttpStatus.OK);
    }

    @GetMapping(value="/international-market/{id}",params = {"currency"})
    public ResponseEntity<List<ProductListBatchDTO>> getAllProductsWithConvertedPrice(@PathVariable Long id,@RequestParam String currency){
        return new ResponseEntity<>(service.getByProductIdWithConvertedPrice(id,currency),HttpStatus.OK);
    }
}
