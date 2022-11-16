package com.bootcamp.melifrescos.controller;

import com.bootcamp.melifrescos.dto.PurchaseOrderRequest;
import com.bootcamp.melifrescos.dto.PurchaseOrderResponse;
import com.bootcamp.melifrescos.dto.PurchaseOrderProductDTO;
import com.bootcamp.melifrescos.interfaces.IPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public ResponseEntity<PurchaseOrderResponse> postPurchaseOrder(@RequestBody @Valid PurchaseOrderRequest purchaseOrder) {
        return new ResponseEntity<>(service.create(purchaseOrder), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<PurchaseOrderProductDTO>> getProductsByPurchaseOrder(@PathVariable Long id) {
        return new ResponseEntity<>(service.getProductsByPurchaseOrder(id), HttpStatus.OK);
    }
}
