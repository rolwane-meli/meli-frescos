package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.enums.OrderStatus;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.exceptions.PurchaseAlreadyFinishedException;
import com.bootcamp.melifrescos.exceptions.UnavailableVolumeException;
import com.bootcamp.melifrescos.model.Buyer;
import com.bootcamp.melifrescos.model.PurchaseOrder;
import com.bootcamp.melifrescos.repository.IPurchaseOrderRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PurchaseOrderServiceTest {

    @InjectMocks
    private PurchaseOrderService service;

    @Mock
    private IPurchaseOrderRepo repo;

    private PurchaseOrder purchaseOrder, purchaseOrderFinished;

    @BeforeEach
    public void setup() {
        purchaseOrder = new PurchaseOrder(1L, LocalDateTime.now(), OrderStatus.OPEN, new Buyer());
        purchaseOrderFinished = new PurchaseOrder(1L, LocalDateTime.now(), OrderStatus.FINISHED, new Buyer());
    }

    @Test
    public void updateStatusToFinished_givenAnExistingId_updateStatus() {
        Mockito.when(repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(purchaseOrder));

        Mockito.when(repo.save(ArgumentMatchers.any()))
                .thenReturn(ArgumentMatchers.any());

        service.updateStatusToFinished(1L);

        assertThat(purchaseOrder.getStatus()).isEqualTo(OrderStatus.FINISHED);
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
