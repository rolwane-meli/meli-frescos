package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.PurchaseOrderProductDTO;
import com.bootcamp.melifrescos.enums.OrderStatus;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.exceptions.PurchaseAlreadyFinishedException;
import com.bootcamp.melifrescos.exceptions.UnavailableVolumeException;
import com.bootcamp.melifrescos.model.Buyer;
import com.bootcamp.melifrescos.model.Product;
import com.bootcamp.melifrescos.model.ProductPurchaseOrder;
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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
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

    private PurchaseOrder purchaseOrder, purchaseOrderFinished;

    private PurchaseOrderProductDTO purchaseOrderProductDTO;


    @BeforeEach
    public void setup() {
        Product product = new Product(1L, "Leite", Type.REFRIGERATED, null, null, null);
        ProductPurchaseOrder productPurchaseOrder = new ProductPurchaseOrder(1L, new BigDecimal("120"), 20, null, null);
        purchaseOrder = new PurchaseOrder(1L, LocalDateTime.now(), OrderStatus.OPEN, new Buyer(), null);
        purchaseOrderFinished = new PurchaseOrder(1L, LocalDateTime.now(), OrderStatus.FINISHED, new Buyer(), null);
        purchaseOrderProductDTO = new PurchaseOrderProductDTO(1L, product.getName(), productPurchaseOrder.getProductPrice(), productPurchaseOrder.getProductQuantity());
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
