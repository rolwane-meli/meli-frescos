package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.ProductStockDTO;
import com.bootcamp.melifrescos.dto.WarehouseRequestDTO;
import com.bootcamp.melifrescos.dto.WarehouseStockDTO;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.interfaces.IRepresentativeService;
import com.bootcamp.melifrescos.model.Representative;
import com.bootcamp.melifrescos.model.Warehouse;
import com.bootcamp.melifrescos.repository.IWarehouseRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {

    @InjectMocks
    WarehouseService warehouseService;

    @Mock
    IRepresentativeService representativeService;

    @Mock
    IWarehouseRepo warehouseRepo;

    private WarehouseRequestDTO warehouseRequestDTO;
    private Warehouse warehouse;
    private Representative representative;
    private ProductStockDTO productStock;
    private WarehouseStockDTO warehouseStock;
    private List<WarehouseStockDTO> warehouses = new ArrayList<>();

    @BeforeEach
    public void setup() {
        representative = new Representative(1L, "Ana Oliveira Reis", "ana.reis@hotmail.com", "90142253790", null);
        warehouseRequestDTO = new WarehouseRequestDTO("meli-ce1", 1L);
        warehouse = new Warehouse(1L, warehouseRequestDTO.getName(), representative, null);
        warehouseStock = new WarehouseStockDTO(1L, "250");
        warehouses.add(warehouseStock);
        productStock = new ProductStockDTO(1L, warehouses);
    }

    @Test
    public void create_givenAValidWarehouseRequestDTO_returnsWarehouse() {
        Mockito.when(representativeService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(representative));

        Mockito.when(warehouseRepo.save(ArgumentMatchers.any()))
                .thenReturn(warehouse);

        Warehouse result = warehouseService.create(warehouseRequestDTO);

        assertThat(result).isEqualTo(warehouse);
    }

    @Test
    public void create_givenAInValidWarehouseRequestDTO_throwsNotFoundException() {
        Mockito.when(representativeService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,() ->  {
            warehouseService.create(warehouseRequestDTO);
        });
    }

    @Test
    public void getById_givenAnValidId_returnsWarehouse(){
        Mockito.when(warehouseRepo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(warehouse));

        Warehouse resultWarehouse = warehouseService.getById(1L).orElse(null);

        assertThat(resultWarehouse).isNotNull();
        assertThat(resultWarehouse).isEqualTo(warehouse);
        assertThat(resultWarehouse.getId()).isPositive();
    }

    @Test
    public void checkProductStock_returnProductStockInWarehouse_whenProductExistInAnyWarehouse(){
        Mockito.when(warehouseRepo.findProductStockInWarehouse(ArgumentMatchers.anyLong()))
                .thenReturn(warehouses);

        ProductStockDTO result = warehouseService.checkProductStock(1L);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(productStock);
        assertThat(result.getWarehouses()).isEqualTo(warehouses);
    }

    @Test
    public void checkProductStock_returnNotFoundException_whenProductNotExistInAnyWarehouse(){
        Mockito.when(warehouseRepo.findProductStockInWarehouse(ArgumentMatchers.anyLong()))
                        .thenReturn(null);

        assertThrows(NotFoundException.class,() ->  {
            warehouseService.checkProductStock(2L);
        });
    }
}
