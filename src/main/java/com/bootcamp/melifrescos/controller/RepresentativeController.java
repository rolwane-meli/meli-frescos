package com.bootcamp.melifrescos.controller;

import com.bootcamp.melifrescos.interfaces.IRepresentativeService;
import com.bootcamp.melifrescos.model.Representative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/fresh-products/representative")
public class RepresentativeController {

    @Autowired
    private IRepresentativeService service;

    /**
     * Método responsável por criar Representative
     * @param representative recebe-se pelo body
     * @return Response entity de representantive
     */
    @PostMapping
    public ResponseEntity<Representative> create(@RequestBody @Valid Representative representative) {
        return new ResponseEntity<>(service.create(representative), HttpStatus.OK);
    }
}
