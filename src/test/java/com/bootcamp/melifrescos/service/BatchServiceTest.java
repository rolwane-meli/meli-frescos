package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.BatchDTO;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.exceptions.BatchNotExistException;
import com.bootcamp.melifrescos.interfaces.IProductService;
import com.bootcamp.melifrescos.model.*;
import com.bootcamp.melifrescos.repository.IBatchRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
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
public class BatchServiceTest {
    @InjectMocks
    private BatchService service;
    @Mock
    private IProductService productService;
    @Mock
    private IBatchRepo repo;

    private Batch batch;
    private BatchDTO batchDTO;
    private List<Batch> batchList = new ArrayList<>();
    private Product product;
    private InboundOrder inboundOrder;

    @BeforeEach
    void setup() {
        product = new Product(1L, "Leite", Type.REFRIGERATED, new Seller(), null, null);
        batchDTO = new BatchDTO(null, 1L, -13.00, 5, LocalDate.now(), LocalTime.now(), 30.00, LocalDateTime.now(), new BigDecimal(7));
        batch = new Batch(1L, 8.00, 5, LocalDate.now(), LocalTime.now(), 30.00, LocalDateTime.now(), new BigDecimal(7), product, null);
        inboundOrder = new InboundOrder(1L,LocalDateTime.now(),new Sector(),null);
        batchList.add(batch);
    }

    @Test
    void create_returnBatch_whenCaseOfSuccess(){
        Mockito.when(productService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(product));

        BDDMockito.when(repo.save(ArgumentMatchers.any(Batch.class)))
                .thenReturn(batch);

        Batch resultBatch = service.create(batchDTO);

        assertThat(resultBatch).isNotNull();
        assertThat(resultBatch.getId()).isPositive();
        assertThat(resultBatch).isEqualTo(batch);
    }

    @Test
    void create_returnException_whenFailureCase() {
        Mockito.when(productService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(null);

        assertThrows(RuntimeException.class,() ->  {
            service.create(batchDTO);
        });
    }

    @Test
    void createAll_returnBatch_whenCaseOfSuccess(){
        Mockito.when(productService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(product));

        BDDMockito.when(repo.saveAll(ArgumentMatchers.anyList()))
                .thenReturn(batchList);

        List<BatchDTO> batchDTOList = new ArrayList<>();
        batchDTOList.add(batchDTO);

        List<Batch> resultBatch = service.createAll(batchDTOList, inboundOrder);

        assertThat(resultBatch).isNotNull();
        assertThat(resultBatch.get(0)).isEqualTo(batchList.get(0));
        assertThat(resultBatch).isEqualTo(batchList);
    }

    @Test
    void createAll_returnException_whenFailureCase() {
        Mockito.when(productService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(null);

        assertThrows(RuntimeException.class,() ->  {
            service.create(batchDTO);
        });
    }

    @Test
    void getById_returnBatch_whenBatchExist() throws BatchNotExistException {
        Mockito.when(repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(batch));

        Batch resultBatch = service.getById(1L).get();

        assertThat(resultBatch).isNotNull();
        assertThat(resultBatch).isEqualTo(batch);
        assertThat(resultBatch.getId()).isPositive();
    }

    @Test
    void getById_returnException_whenBatchNotExist(){
        Mockito.when(repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(BatchNotExistException.class, () -> {
            service.getById(999L);
        });
    }

    @Test
    void getBatchesBySector_returnBatches_whenbatchExistInSector(){
        List<BatchDTO> batchList = new ArrayList<>();
        batchList.add(batchDTO);

        Mockito.when(repo.findBatchesBySectorAndDurDate(ArgumentMatchers.any(), ArgumentMatchers.anyLong()))
                .thenReturn(batchList);

        List<BatchDTO> result = service.getBatchesBySector(1L, 90);

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(batchList.size());
        assertThat(result.get(0)).isEqualTo(batchDTO);
    }
}
