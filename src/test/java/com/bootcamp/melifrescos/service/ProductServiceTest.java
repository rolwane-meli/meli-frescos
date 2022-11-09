package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.ProductRequestDTO;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.model.Product;
import com.bootcamp.melifrescos.model.Seller;
import com.bootcamp.melifrescos.repository.IProductRepo;
import com.bootcamp.melifrescos.repository.ISellerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService service;

    @InjectMocks
    private SellerService serviceSeller;

    @Mock
    private IProductRepo repo;

    @Mock
    private ISellerRepo repoSeller;

    private Product product;

    private ProductRequestDTO productRequestDTO;
    private Seller seller;

    @BeforeEach
    void setup() {
        seller = new Seller(1L,"joao","31999999999","joao@email.com","12345678912345",null);
        product = new Product(1L,"leite", Type.REFRIGERATED,seller,null);
        productRequestDTO = new ProductRequestDTO("leite",Type.REFRIGERATED,1L);
    }

    @Test
    void create_returnProduct_whenCaseOfSuccess(){
        Mockito.when(serviceSeller.getById(ArgumentMatchers.anyLong()))
                        .thenReturn(seller);

        Mockito.when(repo.save(ArgumentMatchers.any()))
                .thenReturn(product);

        Product product1 = service.create(productRequestDTO);

        assertThat(product1.getName()).isEqualTo(productRequestDTO.getName());
    }
}
