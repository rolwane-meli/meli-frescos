package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.interfaces.ISectorService;
import com.bootcamp.melifrescos.model.Sector;
import com.bootcamp.melifrescos.model.Warehouse;
import com.bootcamp.melifrescos.repository.ISectorRepo;
import com.bootcamp.melifrescos.repository.IWarehouseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SectorService implements ISectorService {

    private final ISectorRepo repo;
    private final IWarehouseRepo wareRepo;

    @Override
    public Sector create(Sector sector) {
        Long warehouseId = sector.getWarehouse().getId();

        if (isWarehouseExists(warehouseId)) return null;

        return repo.save(sector);
    }

    @Override
    public boolean isWarehouseExists(Long id) {
        Optional<Warehouse> warehouseOptional = wareRepo.findById(id);

        return warehouseOptional.isPresent();
    }


}
