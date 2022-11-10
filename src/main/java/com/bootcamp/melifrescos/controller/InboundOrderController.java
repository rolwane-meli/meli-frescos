package com.bootcamp.melifrescos.controller;

import com.bootcamp.melifrescos.dto.InboundOrderDTO;
import com.bootcamp.melifrescos.interfaces.IInboundOrderService;
import com.bootcamp.melifrescos.model.InboundOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/fresh-products/inboundorder")
public class InboundOrderController {

    @Autowired
    private IInboundOrderService service;

    @PostMapping
    public ResponseEntity<InboundOrder> create(@RequestBody @Valid InboundOrderDTO inboundOrder) {
        return new ResponseEntity<>(service.create(inboundOrder), HttpStatus.CREATED);
    }
}
