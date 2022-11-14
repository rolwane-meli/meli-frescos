package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.PurchaseOrderRequest;
import com.bootcamp.melifrescos.dto.PurchaseOrderResponse;
import com.bootcamp.melifrescos.dto.PurchaseProductDTO;
import com.bootcamp.melifrescos.enums.OrderStatus;
import com.bootcamp.melifrescos.exceptions.NoQuantityBatchProduct;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.exceptions.PurchaseAlreadyFinishedException;
import com.bootcamp.melifrescos.interfaces.IBatchService;
import com.bootcamp.melifrescos.interfaces.IBuyerService;
import com.bootcamp.melifrescos.interfaces.IProductPurchaseOrderService;
import com.bootcamp.melifrescos.interfaces.IPurchaseOrderService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class PurchaseOrderServiceTest {

    @InjectMocks
    private PurchaseOrderService service;
    @Mock
    private IPurchaseOrderRepo repo;
    @Mock
    private IBatchService batchService;

    @Mock
    private IBuyerService buyerService;

    @Mock
    private IProductPurchaseOrderService productPurchaseService;

    private PurchaseOrder purchaseOrder, purchaseOrderFinished;
    private Batch batch;
    private PurchaseOrderRequest purchaseRequest;

    private PurchaseOrderResponse purchaseResponse;

    private List<ProductPurchaseOrder> productPurchaseOrder = new ArrayList<>();

    @BeforeEach
    public void setup() {
        purchaseOrder = new PurchaseOrder(1L, LocalDateTime.now(), OrderStatus.OPEN, new Buyer());
        purchaseOrderFinished = new PurchaseOrder(1L, LocalDateTime.now(), OrderStatus.FINISHED, new Buyer());
        batch = new Batch(1L, 12, 12, LocalDate.now(), LocalTime.now(), 1,
                LocalDateTime.now(), new BigDecimal("0"), new Product(), new InboundOrder());
        purchaseRequest = new PurchaseOrderRequest(1L, 1L, 1L, new PurchaseProductDTO(1L, 1, new BigDecimal("0")));
        purchaseResponse = new PurchaseOrderResponse(OrderStatus.OPEN, new BigDecimal("0"), productPurchaseOrder);
       productPurchaseOrder.add(new ProductPurchaseOrder(1L, new BigDecimal("0"), 1, 1L, purchaseOrder, batch.getProduct()));
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

    @Test
    public void create_returnThrowException_whenQuantityBatchMinorQuantityBuyer() {
        Mockito.when(buyerService.getById(ArgumentMatchers.anyLong()))
                        .thenReturn(Optional.of(new Buyer()));
        Mockito.when(batchService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(new Batch()));

        assertThrows(NoQuantityBatchProduct.class, () -> {
            service.create(purchaseRequest);
        });
    }

    @Test
    public void create_returnPurchaseOrderResponse_whenPayloadSuccess() {
        Mockito.when(buyerService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(new Buyer()));
        Mockito.when(batchService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(batch));
        Mockito.when(repo.save(ArgumentMatchers.any()))
                        .thenReturn(purchaseOrder);
        Mockito.when(productPurchaseService.create(ArgumentMatchers.any()))
                .thenReturn(new ProductPurchaseOrder());
        Mockito.when(productPurchaseService.getAllProductPurchaseOrder(ArgumentMatchers.any()))
                .thenReturn(productPurchaseOrder);

        PurchaseOrderResponse response = service.create(purchaseRequest);

        assertThat(response.getStatus()).isEqualTo(purchaseResponse.getStatus());
        assertThat(response.getTotalPrice()).isEqualTo(purchaseResponse.getTotalPrice());
        assertThat(response.getProducts()).isEqualTo(purchaseResponse.getProducts());

    }

}
