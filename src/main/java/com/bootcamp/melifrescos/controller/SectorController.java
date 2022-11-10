package com.bootcamp.melifrescos.controller;

import com.bootcamp.melifrescos.dto.SectorRequestDTO;
import com.bootcamp.melifrescos.model.Sector;
import com.bootcamp.melifrescos.service.SectorService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/fresh-products/sector")
@RequiredArgsConstructor
public class SectorController {

    private final SectorService service;

    /**
     * This function creates a new sector and returns it
     *
     * @param sector The object that will be created.
     * @return A ResponseEntity object is being returned.
     */
    @PostMapping
    public ResponseEntity<Sector> create(@RequestBody @Valid SectorRequestDTO sector) {
        return new ResponseEntity<>(service.create(sector), HttpStatus.CREATED);
    }
}
