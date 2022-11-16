package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.ProductListDTO;
import com.bootcamp.melifrescos.dto.ProductRequestDTO;
import com.bootcamp.melifrescos.dto.ProductWithBatchesDTO;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.model.*;
import com.bootcamp.melifrescos.repository.IProductRepo;
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
public class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private SellerService serviceSeller;

    @Mock
    private IProductRepo repo;

    private Product product;

    private ProductRequestDTO productRequestDTO;

    private ProductRequestDTO productRequestDTOFail;
    private Seller seller;
    private List<ProductListDTO> productsList = new ArrayList<>();
    private ProductListDTO productListDTO;

    private ProductWithBatchesDTO productWithBatchesDTO;

    private List<Batch> batchList = new ArrayList<>();

    private InboundOrder inboundOrder;
    private Batch batch;

    private Batch batch1;

    private Sector sector;

    private Warehouse warehouse;




    @BeforeEach
    void setup() {
        warehouse = new Warehouse(1L, "wherehouse", null, null);
        sector = new Sector(1L, "meli-ce1", 100, Type.FROZEN, warehouse, null);
        inboundOrder = new InboundOrder(1L, LocalDateTime.now(), sector,null);
        batch = new Batch(1L, 8.00, 5, LocalDate.now(), LocalTime.now(), 30.00, LocalDateTime.now(), new BigDecimal(7), product, inboundOrder);
        batch1 = new Batch(2L, 6.00, 2, LocalDate.now(), LocalTime.now(), 1.00, LocalDateTime.of(2022,10,5,9,39,53), new BigDecimal(7), product, inboundOrder);
        batchList.add(batch);
        batchList.add(batch1);

        seller = new Seller(1L,"joao","31999999999","joao@email.com","12345678912345",null);
        product = new Product(1L,"leite", Type.REFRIGERATED, seller, batchList, null);
        productRequestDTO = new ProductRequestDTO("leite",Type.REFRIGERATED.name(),1L);
        productRequestDTOFail = new ProductRequestDTO("leite",Type.REFRIGERATED.name(),2L);
        productListDTO = new ProductListDTO(1L, "CHOCOLATE", Type.FROZEN, new BigDecimal(5), 5, 2L);
        productsList.add(productListDTO);
        productWithBatchesDTO = new ProductWithBatchesDTO(product);
    }

    @Test
    void create_returnProduct_whenCaseOfSuccess(){
        Mockito.when(serviceSeller.getById(ArgumentMatchers.anyLong()))
                        .thenReturn(seller);

        Mockito.when(repo.save(ArgumentMatchers.any()))
                .thenReturn(product);

        Product product1 = service.create(productRequestDTO);

        assertThat(product1.getName()).isEqualTo("leite");
    }

    @Test
    void create_returnException_whenFailureCase() {
        Mockito.when(serviceSeller.getById(ArgumentMatchers.anyLong()))
                .thenReturn(null);

        assertThrows(NotFoundException.class,() ->  {
            service.create(productRequestDTOFail);
        });
    }

    @Test
    void getById_returnProduct_whenProductExist(){
        Mockito.when(repo.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(product));

        Optional<Product> productResult = service.getById(1L);

        assertThat(productResult.isPresent()).isTrue();
        assertThat(productResult.get().getId()).isPositive();
        assertThat(productResult.get().getName()).isEqualTo(product.getName());
        assertThat(productResult.get().getType()).isEqualTo(product.getType());
    }

    @Test
    void findProductsByBatches_returnListOfProductsListDTO_whenProductIsInABatch(){
        Mockito.when(repo.findProductsByBatches())
                .thenReturn(productsList);

        List<ProductListDTO> productListResult = service.findProductsByBatches();

        assertThat(productListResult).isNotNull();
        assertThat(productListResult).isNotEmpty();
        assertThat(productListResult.get(0)).isEqualTo(productListDTO);
        assertThat(productListResult.size()).isEqualTo(productsList.size());
    }

    @Test
    void findProductsByBatchesAndType_returnListOfProductsListDTO_whenProductIsInABatch(){
        Mockito.when(repo.findProductsByBatchesAndType(ArgumentMatchers.any(Type.class)))
                .thenReturn(productsList);

        List<ProductListDTO> productListResult = service.findProductsByBatchesAndType(Type.FROZEN);

        assertThat(productListResult).isNotNull();
        assertThat(productListResult).isNotEmpty();
        assertThat(productListResult.get(0)).isEqualTo(productListDTO);
        assertThat(productListResult.size()).isEqualTo(productsList.size());
    }

    @Test
    void getByIdWithBatches_returnProductWithBatchesDTO_whenThereIsProduct(){
        Mockito.when(repo.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(product));

        ProductWithBatchesDTO productWithBatchesResponse = service.getByIdWithBatches(1L);

        assertThat(productWithBatchesResponse.getName()).isEqualTo(product.getName());
        assertThat(productWithBatchesResponse.getBatchStock().get(0).getBatchNumber()).isEqualTo(batch.getId());
        assertThat(productWithBatchesResponse.getBatchStock().get(0).getSector()).isEqualTo(sector.getId());
        assertThat(productWithBatchesResponse.getBatchStock().get(0).getWarehouse()).isEqualTo(warehouse.getId());
    }

    @Test
    void getByIdWithSortedBatches_returnGetByIdWithSortedBatchesByBatch_whenThereIsProduct(){
        Mockito.when(repo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(product));

        ProductWithBatchesDTO productWithBatchesSorted = service.getByIdWithSortedBatches(1L,"l");

        assertThat(productWithBatchesSorted.getName()).isEqualTo(productWithBatchesDTO.getName());
        assertThat(productWithBatchesSorted.getBatchStock().get(1).getBatchNumber()).isEqualTo(batch1.getId());
    }

    @Test
    void getByIdWithSortedBatches_returnGetByIdWithSortedBatchesByAmount_whenThereIsProduct(){
        Mockito.when(repo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(product));

        ProductWithBatchesDTO productWithBatchesSorted = service.getByIdWithSortedBatches(1L,"q");

        assertThat(productWithBatchesSorted.getName()).isEqualTo(productWithBatchesDTO.getName());
        assertThat(productWithBatchesSorted.getBatchStock().size()).isEqualTo(batchList.size());
        assertThat(productWithBatchesSorted.getBatchStock().get(0).getBatchNumber()).isEqualTo(batch1.getId());
        assertThat(productWithBatchesSorted.getBatchStock().get(1).getCurrentQuantity()).isEqualTo(batch.getProductQuantity());
    }

    @Test
    void getByIdWithSortedBatches_returnGetByIdWithSortedBatchesByDueDate_whenThereIsProduct(){
        Mockito.when(repo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(product));

        ProductWithBatchesDTO productWithBatchesSorted = service.getByIdWithSortedBatches(1L,"v");

        assertThat(productWithBatchesSorted.getName()).isEqualTo(productWithBatchesDTO.getName());
        assertThat(productWithBatchesSorted.getBatchStock().size()).isEqualTo(batchList.size());
        assertThat(productWithBatchesSorted.getBatchStock().get(0).getBatchNumber()).isEqualTo(batch1.getId());
    }

    @Test
    void getByIdWithSortedBatches_returnNotFoundException_whenTheTypeDoesNotExist(){
        Mockito.when(repo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(product));

        assertThrows(NotFoundException.class,()-> service.getByIdWithSortedBatches(1L,"w"));
    }
}
