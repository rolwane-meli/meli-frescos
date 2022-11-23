package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.ProductStockDTO;
import com.bootcamp.melifrescos.dto.WarehouseRequestDTO;
import com.bootcamp.melifrescos.dto.WarehouseStockDTO;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.interfaces.IRepresentativeService;
import com.bootcamp.melifrescos.interfaces.IWarehouseService;
import com.bootcamp.melifrescos.model.Representative;
import com.bootcamp.melifrescos.model.Warehouse;
import com.bootcamp.melifrescos.repository.IWarehouseRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    /**
     * Método responsável por buscar Warehouse
     * @param  id via requisicao
     * @return Optinal de Warehouse
     */
    @Override
    public Optional<Warehouse> getById(Long id) {
        return repo.findById(id);
    }

    /**
     * Método responsável por checar o estoque de determinado produto nos armazéns
     * @param productId Id do produto
     * @return Retorna uma DTO com o Id do produto e os armazéns em que o mesmo está armazenado com suas respectivas quantidades
     */
    @Override
    public ProductStockDTO checkProductStock(Long productId){
        List<WarehouseStockDTO> warehouseStockList = repo.findProductStockInWarehouse(productId);

        if (warehouseStockList==null || warehouseStockList.isEmpty()){
            throw new NotFoundException("Produto não está alocado em nenhum armazém");
        }

        return new ProductStockDTO(productId, warehouseStockList);
    }
}
