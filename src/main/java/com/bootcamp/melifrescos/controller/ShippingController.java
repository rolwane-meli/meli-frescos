package com.bootcamp.melifrescos.controller;

import com.bootcamp.melifrescos.dto.ShippingResponseDTO;
import com.bootcamp.melifrescos.interfaces.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/shipping")
public class ShippingController {

    @Autowired
    private IShippingService service;

    @GetMapping
    public ResponseEntity<ShippingResponseDTO> getPrice(@RequestParam String cep, @RequestParam Long productId, @RequestParam int quantity) {
        return new ResponseEntity<>(service.getShippingPrice(cep, productId, quantity), HttpStatus.OK);
    }
}
