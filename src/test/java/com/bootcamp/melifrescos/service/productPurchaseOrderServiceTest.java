package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.model.Product;
import com.bootcamp.melifrescos.model.ProductPurchaseOrder;
import com.bootcamp.melifrescos.model.PurchaseOrder;
import com.bootcamp.melifrescos.repository.IProductPurchaseOrderRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class productPurchaseOrderServiceTest {

    @InjectMocks
    private ProductPurchaseOrderService service;

    @Mock
    private IProductPurchaseOrderRepo repo;

    private ProductPurchaseOrder productPurchaseOrder;

    @BeforeEach
    void setUp() {
        productPurchaseOrder = new ProductPurchaseOrder(1L, new BigDecimal(100), 10, 1L, new PurchaseOrder(), new Product());

    }

    @Test
    public void create_returnProductPurchaseOrder_whenPayloadSuccess() {
        Mockito.when(repo.save(ArgumentMatchers.any()))
                .thenReturn(productPurchaseOrder);
        ProductPurchaseOrder response = service.create(productPurchaseOrder);
        assertThat(response.getProductQuantity()).isEqualTo(productPurchaseOrder.getProductQuantity());
        assertThat(response.getProductPrice()).isEqualTo(productPurchaseOrder.getProductPrice());
        assertThat(response.getPurchaseOrder()).isEqualTo(productPurchaseOrder.getPurchaseOrder());
        assertThat(response.getProduct()).isEqualTo(productPurchaseOrder.getProduct());
        assertThat(response.getBatchId()).isEqualTo(productPurchaseOrder.getBatchId());
    }

    @Test
    public void getByPurchaseOrder_returnProductPurchaseOrder_whenIdSuccess() {
        Mockito.when(repo.findProductPurchaseOrderByPurchaseOrder(ArgumentMatchers.any()))
                .thenReturn(productPurchaseOrder);

        ProductPurchaseOrder response = service.getByPurchaseOrder(new PurchaseOrder());

        assertThat(response.getProductQuantity()).isEqualTo(productPurchaseOrder.getProductQuantity());
        assertThat(response.getProductPrice()).isEqualTo(productPurchaseOrder.getProductPrice());
        assertThat(response.getPurchaseOrder()).isEqualTo(productPurchaseOrder.getPurchaseOrder());
        assertThat(response.getProduct()).isEqualTo(productPurchaseOrder.getProduct());
        assertThat(response.getBatchId()).isEqualTo(productPurchaseOrder.getBatchId());
    }

    @Test
    public void getAllByPurchaseOrder_returnListProductPurchase_whenIdSuccess() {
        Mockito.when(repo.findAllProductPurchaseOrderByPurchaseOrder(ArgumentMatchers.any()))
                .thenReturn(Arrays.asList(productPurchaseOrder));
        List<ProductPurchaseOrder> response = service.getAllByPurchaseOrder(new PurchaseOrder());

        assertThat(response.get(0).getProductQuantity()).isEqualTo(productPurchaseOrder.getProductQuantity());
        assertThat(response.get(0).getProductPrice()).isEqualTo(productPurchaseOrder.getProductPrice());
        assertThat(response.get(0).getPurchaseOrder()).isEqualTo(productPurchaseOrder.getPurchaseOrder());
        assertThat(response.get(0).getProduct()).isEqualTo(productPurchaseOrder.getProduct());
        assertThat(response.get(0).getBatchId()).isEqualTo(productPurchaseOrder.getBatchId());
    }
}
