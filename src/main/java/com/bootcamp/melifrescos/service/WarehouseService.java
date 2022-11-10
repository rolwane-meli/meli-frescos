package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.interfaces.IWarehouseService;
import com.bootcamp.melifrescos.model.Warehouse;
import com.bootcamp.melifrescos.repository.IWarehouseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseService implements IWarehouseService {

    private final IWarehouseRepo repo;

    @Override
    public Warehouse create(Warehouse warehouse) {
        return repo.save(warehouse);
    }

    @Override
    public Optional<Warehouse> getById(Long id) { return repo.findById(id); }
}
