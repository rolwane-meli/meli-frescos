package com.bootcamp.melifrescos.service;

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

    @BeforeEach
    void setup() {
        seller = new Seller(1L,"joao","31999999999","joao@email.com","12345678912345",null);
        product = new Product(1L,"leite", Type.REFRIGERATED, seller, null, null);
        productRequestDTO = new ProductRequestDTO("leite",Type.REFRIGERATED.name(),1L);
        productRequestDTOFail = new ProductRequestDTO("leite",Type.REFRIGERATED.name(),2L);
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
}
