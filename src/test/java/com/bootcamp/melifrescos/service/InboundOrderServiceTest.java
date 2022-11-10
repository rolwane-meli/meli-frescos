package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.BatchDTO;
import com.bootcamp.melifrescos.dto.InboundOrderDTO;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.exceptions.UnavailableVolumeException;
import com.bootcamp.melifrescos.model.InboundOrder;
import com.bootcamp.melifrescos.model.Sector;
import com.bootcamp.melifrescos.model.Warehouse;
import com.bootcamp.melifrescos.repository.IInboundOrderRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class InboundOrderServiceTest {

    @InjectMocks
    InboundOrderService service;

    @Mock
    private IInboundOrderRepo repo;

    @Mock
    private SectorService sectorService;

    @Mock
    private  BatchService batchService;

    private InboundOrderDTO inboundOrderDTO;
    private List<BatchDTO> batchList = new ArrayList<>();
    private BatchDTO batch01;
    private BatchDTO batch02;
    private InboundOrder inboundOrder;

    private Sector sector;

    private Sector sectorFail;


    @BeforeEach
    private void setup(){
        batch01 = new BatchDTO(null, 1L, -13.00, 5, LocalDate.now(), LocalTime.now(), 30, LocalDateTime.now(), new BigDecimal(7));
        batch02 = new BatchDTO(null, 2L, -18.00, 25, LocalDate.now(), LocalTime.now(), 45, LocalDateTime.now(), new BigDecimal(9));

        batchList.add(batch01);
        batchList.add(batch02);

        sector = new Sector(1L, "meli-ce1", 100, Type.FROZEN, new Warehouse(), null);
        sectorFail = new Sector(1L, "meli-ce1", 10, Type.FROZEN, new Warehouse(), null);

        inboundOrderDTO = new InboundOrderDTO(1L, LocalDateTime.now(), batchList);
        inboundOrder = new InboundOrder(1L,inboundOrderDTO.getOrderDate(),sector,null);
    }

    @Test
    void create_returnNewInboundOrder_whenCaseOfSucces(){
        Mockito.when(sectorService.getById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(sector));

        Mockito.when(repo.save(ArgumentMatchers.any()))
                .thenReturn(inboundOrder);

        Mockito.when(batchService.createAll(ArgumentMatchers.anyList(),ArgumentMatchers.any()))
                .thenReturn(ArgumentMatchers.anyList());

        InboundOrder newInboundOrder = service.create(inboundOrderDTO);

        assertThat(newInboundOrder).isEqualTo(inboundOrder);
        assertThat(newInboundOrder.getId()).isEqualTo(inboundOrder.getId());
    }

    @Test
    void create_throwNotFoundException_whenSectorDoesNotExist(){
        Mockito.when(sectorService.getById(ArgumentMatchers.any()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,() ->  {
            service.create(inboundOrderDTO);
        });
    }

    @Test
    void create_throwUnavailableVolumeException_whenVolumeDontHaveTheCapacity(){
        Mockito.when(sectorService.getById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(sectorFail));

        assertThrows(UnavailableVolumeException.class,() ->  {
            service.create(inboundOrderDTO);
        });
    }
}
