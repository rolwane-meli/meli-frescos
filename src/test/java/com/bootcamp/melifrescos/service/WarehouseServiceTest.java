package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.WarehouseRequestDTO;
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

    @BeforeEach
    public void setup() {
        representative = new Representative(1L, "Ana Oliveira Reis", "ana.reis@hotmail.com", "90142253790", null);
        warehouseRequestDTO = new WarehouseRequestDTO("meli-ce1", 1L);
        warehouse = new Warehouse(1L, warehouseRequestDTO.getName(), representative, null);
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
}
