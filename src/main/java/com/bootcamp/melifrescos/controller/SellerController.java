package com.bootcamp.melifrescos.controller;

import com.bootcamp.melifrescos.interfaces.ISellerService;
import com.bootcamp.melifrescos.model.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/fresh-products/seller")
public class SellerController {

    @Autowired
    private ISellerService sellerService;

    @PostMapping
    public ResponseEntity<Seller> create(@RequestBody @Valid Seller seller) {
        return new ResponseEntity<>(sellerService.create(seller), HttpStatus.CREATED);
    }
}
