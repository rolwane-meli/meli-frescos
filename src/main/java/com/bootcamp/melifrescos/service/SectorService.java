package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.SectorRequestDTO;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.interfaces.ISectorService;
import com.bootcamp.melifrescos.interfaces.IWarehouseService;
import com.bootcamp.melifrescos.model.Sector;
import com.bootcamp.melifrescos.model.Warehouse;
import com.bootcamp.melifrescos.repository.ISectorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SectorService implements ISectorService {

    private final ISectorRepo repo;
    private final IWarehouseService wareService;

    /**
     * It creates a new sector, given a sector request DTO, and saves it to the database
     * @param sector The object that will be created.
     * @return A Sector object
     */
    @Override
    public Sector create(SectorRequestDTO sector) {
        Long warehouseId = sector.getWarehouseId();

        Warehouse warehouse = wareService.getById(warehouseId).orElse(null);

        if (warehouse == null) throw new NotFoundException("Warehouse não existe!");

        Sector newSector = new Sector(null, sector.getName(), sector.getCapacity(), Type.fromValue(sector.getType()), warehouse, null);
        return repo.save(newSector);
    }

    /**
     * If the sector with the given id exists, return it, otherwise return null.
     * @param id The id of the sector you want to get.
     * @return Optional<Sector>
     */
    @Override
    public Optional<Sector> getById(Long id) {
       return repo.findById(id);
    }
}
