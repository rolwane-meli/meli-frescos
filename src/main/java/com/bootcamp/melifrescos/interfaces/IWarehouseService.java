package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.model.Warehouse;

import java.util.Optional;

public interface IWarehouseService {
    public Warehouse create(Warehouse warehouse);
    public Optional<Warehouse> getById(Long id);
}
