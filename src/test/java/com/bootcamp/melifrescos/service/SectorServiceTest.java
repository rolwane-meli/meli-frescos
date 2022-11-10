package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.SectorRequestDTO;
import com.bootcamp.melifrescos.dto.WarehouseRequestDTO;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.model.Representative;
import com.bootcamp.melifrescos.model.Sector;
import com.bootcamp.melifrescos.model.Warehouse;
import com.bootcamp.melifrescos.repository.ISectorRepo;
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
public class SectorServiceTest {
    @InjectMocks
    SectorService service;

    @Mock
    ISectorRepo repo;

    @Mock
    WarehouseService warehouseService;

    private SectorRequestDTO sectorRequestDTO;

    private Warehouse warehouse;

    private Sector sector;

    @BeforeEach
    public void setup() {
        Representative representative = new Representative(1L, "Ana Oliveira Reis", "ana.reis@hotmail.com", "90142253790", null);
        WarehouseRequestDTO warehouseRequestDTO = new WarehouseRequestDTO("meli-ce1", 1L);
        warehouse = new Warehouse(1L, warehouseRequestDTO.getName(), representative, null);
        sectorRequestDTO = new SectorRequestDTO("meli-ref-01", 120.0, Type.REFRIGERATED.toString(), 1L);
        sector = new Sector(1L, sectorRequestDTO.getName(), sectorRequestDTO.getCapacity(), Type.fromValue(sectorRequestDTO.getType()), warehouse, null);
    }

    @Test
    void create_giveAnValidSectorRequestDTO_returnNewSector() {
        Mockito.when(warehouseService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(warehouse));

        Mockito.when(repo.save(ArgumentMatchers.any(Sector.class)))
                .thenReturn(sector);

        Sector resultSector = service.create(sectorRequestDTO);

        assertThat(resultSector).isNotNull();
        assertThat(resultSector.getId()).isPositive();
        assertThat(resultSector).isEqualTo(sector);
    }

    @Test
    void create_giveAnInvalidWarehouseId_throwsNotFoundException() {
        Mockito.when(warehouseService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.create(sectorRequestDTO));
    }

    @Test
    public void getById_givenAnValidId_returnsWarehouse(){
        Mockito.when(repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(sector));

        Sector resultSector = service.getById(1L).orElse(null);

        assertThat(resultSector).isNotNull();
        assertThat(resultSector).isEqualTo(sector);
        assertThat(resultSector.getId()).isPositive();
    }
}
