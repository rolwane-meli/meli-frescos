package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.WarehouseRequestDTO;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.interfaces.IRepresentativeService;
import com.bootcamp.melifrescos.interfaces.IWarehouseService;
import com.bootcamp.melifrescos.model.Representative;
import com.bootcamp.melifrescos.model.Warehouse;
import com.bootcamp.melifrescos.repository.IWarehouseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseService implements IWarehouseService {

    private final IWarehouseRepo repo;

    private final IRepresentativeService representativeService;

    /**
     * Método que insere um novo armazém no banco de dados.
     * @param warehouseRequestDTO
     * @return Warehouse
     */
    @Override
    public Warehouse create(WarehouseRequestDTO warehouseRequestDTO) {
        Optional<Representative> representative = representativeService.getById(warehouseRequestDTO.getRepresentativeId());

        if (representative.isEmpty()) {
            throw new NotFoundException("Representante não encontrado");
        }

        Warehouse warehouse = new Warehouse(null, warehouseRequestDTO.getName(), representative.get(), null);

        return repo.save(warehouse);
    }
}
