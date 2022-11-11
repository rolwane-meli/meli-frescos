package com.bootcamp.melifrescos.controller;

import com.bootcamp.melifrescos.interfaces.IPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fresh-products/orders")
public class PurchaseOrderController {

    @Autowired
    private IPurchaseOrderService service;

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStatusToFinished(@PathVariable Long id) {
        service.updateStatusToFinished(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
