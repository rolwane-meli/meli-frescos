package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.ProductListDTO;
import com.bootcamp.melifrescos.dto.ProductRequestDTO;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.model.Product;
import com.bootcamp.melifrescos.model.Seller;
import com.bootcamp.melifrescos.repository.IProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
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

    @BeforeEach
    void setup() {
        seller = new Seller(1L,"joao","31999999999","joao@email.com","12345678912345",null);
        product = new Product(1L,"leite", Type.REFRIGERATED,seller,null);
        productRequestDTO = new ProductRequestDTO("leite",Type.REFRIGERATED.name(),1L);
        productRequestDTOFail = new ProductRequestDTO("leite",Type.REFRIGERATED.name(),2L);
        productListDTO = new ProductListDTO(1L, "CHOCOLATE", Type.FROZEN, new BigDecimal(5), 5, 2L);
        productsList.add(productListDTO);
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

        assertThrows(RuntimeException.class,() ->  {
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
}
