package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.BatchDTO;
import com.bootcamp.melifrescos.dto.InboundOrderDTO;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.exceptions.InvalidSectorTypeException;
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
    private InboundOrderService inboundOrderService;

    @Mock
    private IInboundOrderRepo inboundOrderRepo;

    @Mock
    private SectorService sectorService;

    @Mock
    private BatchService batchService;

    private InboundOrderDTO inboundOrderDTO, invalidInboundOrderDTO;
    private InboundOrder inboundOrder;
    private Sector sector, sectorFail;
    private List<BatchDTO> batchList = new ArrayList<>();
    private List<BatchDTO> invalidBacthList = new ArrayList<>();
    private BatchDTO batch01;
    private BatchDTO batch02;
    private BatchDTO batch03;

    @BeforeEach
    public void setup() {
        batch01 = new BatchDTO(null, 1L, -13.00, 5, LocalDate.now(), LocalTime.now(), 30, LocalDateTime.now(), new BigDecimal(7));
        batch02 = new BatchDTO(null, 2L, -18.00, 25, LocalDate.now(), LocalTime.now(), 45, LocalDateTime.now(), new BigDecimal(9));
        batch03 = new BatchDTO(null, 3L, 10.00, 155, LocalDate.now(), LocalTime.now(), 20, LocalDateTime.now(), new BigDecimal(10));

        // Lista de Batches do mesmo tipo
        batchList.add(batch01);
        batchList.add(batch02);

        // Lista de Batches de tipos diferentes
        invalidBacthList.add(batch01);
        invalidBacthList.add(batch03);

        inboundOrderDTO = new InboundOrderDTO(1L, LocalDateTime.now(), batchList);
        invalidInboundOrderDTO = new InboundOrderDTO(1L, LocalDateTime.now(), invalidBacthList);

        sector = new Sector(1L, "meli-ce1", 100, Type.FROZEN, new Warehouse(), null);
        inboundOrder = new InboundOrder(1L, inboundOrderDTO.getOrderDate(), sector,null);

        sectorFail = new Sector(1L, "meli-ce1", 10, Type.FROZEN, new Warehouse(), null);
    }

    @Test
    public void update_givenAnIdAndAInboundOrderDTO_returnsInboundOrder() {
        Mockito.when(inboundOrderService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(new InboundOrder()));

        Mockito.when(sectorService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(sector));

        Mockito.when(inboundOrderRepo.save(ArgumentMatchers.any()))
                .thenReturn(inboundOrder);

        Mockito.when(batchService.createAll(ArgumentMatchers.anyList(), ArgumentMatchers.any()))
                .thenReturn(ArgumentMatchers.anyList());

        InboundOrder result = inboundOrderService.update(1L, inboundOrderDTO);

        assertThat(result).isEqualTo(inboundOrder);
        assertThat(result.getId()).isEqualTo(inboundOrder.getId());
    }

    @Test
    public void update_givenAnInvalidId_throwNotFoundException() {
        Mockito.when(inboundOrderService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->  {
            inboundOrderService.update(1L, inboundOrderDTO);
        });
    }

    @Test
    public void update_givenAnInvalidInboundOrderDTO_throwInvalidSectorTypeException() {
        Mockito.when(inboundOrderService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(new InboundOrder()));

        Mockito.when(sectorService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(sector));

        assertThrows(InvalidSectorTypeException.class, () ->  {
            inboundOrderService.update(1L, invalidInboundOrderDTO);
        });
    }

    @Test
    void create_returnNewInboundOrder_whenCaseOfSucces(){
        Mockito.when(sectorService.getById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(sector));

        Mockito.when(inboundOrderRepo.save(ArgumentMatchers.any()))
                .thenReturn(inboundOrder);

        Mockito.when(batchService.createAll(ArgumentMatchers.anyList(),ArgumentMatchers.any()))
                .thenReturn(ArgumentMatchers.anyList());

        InboundOrder newInboundOrder = inboundOrderService.create(inboundOrderDTO);

        assertThat(newInboundOrder).isEqualTo(inboundOrder);
        assertThat(newInboundOrder.getId()).isEqualTo(inboundOrder.getId());
    }

    @Test
    void create_throwNotFoundException_whenSectorDoesNotExist(){
        Mockito.when(sectorService.getById(ArgumentMatchers.any()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,() ->  {
            inboundOrderService.create(inboundOrderDTO);
        });
    }

    @Test
    void create_throwUnavailableVolumeException_whenVolumeDontHaveTheCapacity(){
        Mockito.when(sectorService.getById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(sectorFail));

        assertThrows(UnavailableVolumeException.class,() ->  {
            inboundOrderService.create(inboundOrderDTO);
        });
    }
}
