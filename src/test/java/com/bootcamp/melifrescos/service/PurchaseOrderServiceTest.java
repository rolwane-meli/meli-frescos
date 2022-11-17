package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.PurchaseOrderProductDTO;
import com.bootcamp.melifrescos.dto.PurchaseOrderRequest;
import com.bootcamp.melifrescos.dto.PurchaseOrderResponse;
import com.bootcamp.melifrescos.dto.PurchaseProductDTO;
import com.bootcamp.melifrescos.enums.OrderStatus;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.exceptions.NoQuantityBatchProduct;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private ProductPurchaseOrderService productPurchaseOrderService;

    @Mock
    private BatchService batchService;

    @Mock
    private BuyerService buyerService;

    private PurchaseOrder purchaseOrder, purchaseOrderFinished;

    private Batch batch;

    private PurchaseOrderRequest purchaseRequest;

    private PurchaseOrderResponse purchaseResponse;

    private List<ProductPurchaseOrder> productPurchaseOrders = new ArrayList<>(), productPurchaseList = new ArrayList<>();

    private Product product;

    private ProductPurchaseOrder productPurchaseOrder;

    private PurchaseOrderProductDTO purchaseOrderProductDTO;

    @BeforeEach
    public void setup() {
        purchaseOrder = new PurchaseOrder(1L, LocalDateTime.now(), OrderStatus.OPEN, new Buyer());
        productPurchaseOrder = new ProductPurchaseOrder(1L, new BigDecimal("5.50"), 20, 1L, purchaseOrder, product);
        batch = new Batch(1L, 10, 20, LocalDate.now(), LocalTime.now(), 75, LocalDateTime.now(), new BigDecimal("5.50"), new Product(), null);

        purchaseRequest = new PurchaseOrderRequest(1L, 1L, 1L, new PurchaseProductDTO(1L, 1, new BigDecimal("0")));
        purchaseResponse = new PurchaseOrderResponse(OrderStatus.OPEN, new BigDecimal("0"), productPurchaseList);
        productPurchaseList.add(new ProductPurchaseOrder(1L, new BigDecimal("0"), 1, 1L, purchaseOrder, batch.getProduct()));
        product = new Product(1L, "Leite", Type.REFRIGERATED, null, null, null);

        purchaseOrderFinished = new PurchaseOrder(1L, LocalDateTime.now(), OrderStatus.FINISHED, new Buyer());

        productPurchaseOrders.add(productPurchaseOrder);
        purchaseOrderProductDTO = new PurchaseOrderProductDTO(1L, product.getName(), productPurchaseOrder.getProductPrice(), productPurchaseOrder.getProductQuantity());
    }

    @Test
    public void updateStatusToFinished_givenAnExistingId_updateStatus() {

        Mockito.when(repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(purchaseOrder));

        Mockito.when(productPurchaseOrderService.getAllByPurchaseOrder(ArgumentMatchers.any()))
                .thenReturn(productPurchaseOrders);

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
        Mockito.when(productPurchaseOrderService.create(ArgumentMatchers.any()))
                .thenReturn(new ProductPurchaseOrder());
        Mockito.when(productPurchaseOrderService.getAllByPurchaseOrder(ArgumentMatchers.any()))
                .thenReturn(productPurchaseList);

        PurchaseOrderResponse response = service.create(purchaseRequest);

        assertThat(response.getStatus()).isEqualTo(purchaseResponse.getStatus());
        assertThat(response.getTotalPrice()).isEqualTo(purchaseResponse.getTotalPrice());
        assertThat(response.getProducts()).isEqualTo(purchaseResponse.getProducts());

    }

    @Test
    void getProductsByPurchaseOrder_giveAnValidPurchaseOrderId_returnAListOfProducts() {
        Mockito.when(repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(purchaseOrder));

        Mockito.when(repo.findProductsByPurchaseOrder(ArgumentMatchers.anyLong()))
                .thenReturn(List.of(purchaseOrderProductDTO));

        List<PurchaseOrderProductDTO> resultPurchaseOrderProduct = service.getProductsByPurchaseOrder(1L);

        assertThat(resultPurchaseOrderProduct).isNotNull();
        assertThat(resultPurchaseOrderProduct.get(0).getId()).isPositive();
        assertThat(resultPurchaseOrderProduct.get(0)).isEqualTo(purchaseOrderProductDTO);
    }
}
