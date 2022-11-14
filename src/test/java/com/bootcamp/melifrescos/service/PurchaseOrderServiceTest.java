package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.enums.OrderStatus;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.exceptions.PurchaseAlreadyFinishedException;
import com.bootcamp.melifrescos.exceptions.UnavailableVolumeException;
import com.bootcamp.melifrescos.model.*;
import com.bootcamp.melifrescos.repository.IPurchaseOrderRepo;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PurchaseOrderServiceTest {

    @InjectMocks
    private PurchaseOrderService service;

    @Mock
    private ProductPurchaseOrderService productPurchaseOrderService;

    @Mock
    private BatchService batchService;

    @Mock
    private IPurchaseOrderRepo repo;

    private PurchaseOrder purchaseOrder, purchaseOrderFinished;

    private Product product;

    private ProductPurchaseOrder productPurchaseOrder;

    private Batch batch;

    @BeforeEach
    public void setup() {
        product = new Product(1L, "Leite", Type.REFRIGERATED, null, null, null);
        purchaseOrder = new PurchaseOrder(1L, LocalDateTime.now(), OrderStatus.OPEN, new Buyer());
        productPurchaseOrder = new ProductPurchaseOrder(1L, new BigDecimal("5.50"), 20, 1L, purchaseOrder, null);
        batch = new Batch(1L, 10, 20, LocalDate.now(), LocalTime.now(), 75, LocalDateTime.now(), new BigDecimal("5.50"), product, null);
        purchaseOrderFinished = new PurchaseOrder(1L, LocalDateTime.now(), OrderStatus.FINISHED, new Buyer());
    }

    @Test
    public void updateStatusToFinished_givenAnExistingId_updateStatus() {
        Mockito.when(repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(purchaseOrder));

        Mockito.when(productPurchaseOrderService.getByPurchaseOrder(ArgumentMatchers.any()))
                .thenReturn(productPurchaseOrder);

        Mockito.when(batchService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(batch));

        Mockito.when(repo.save(ArgumentMatchers.any()))
                .thenReturn(ArgumentMatchers.any());

        service.updateStatusToFinished(1L);

        assertThat(purchaseOrder.getStatus()).isEqualTo(OrderStatus.FINISHED);
        assertThat(batch.getVolume()).isEqualTo(0);
    }

    @Test
    public void updateStatusToFinished_givenANonExistingId_throwsException() {
        Mockito.when(repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,() ->  {
            service.updateStatusToFinished(1L);
        });
    }

    @Test
    public void updateStatusToFinished_givenAnAlreadyFinishedPurchaseOrder_throwsException() {
        Mockito.when(repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(purchaseOrderFinished));

        assertThrows(PurchaseAlreadyFinishedException.class,() ->  {
            service.updateStatusToFinished(1L);
        });
    }

}
