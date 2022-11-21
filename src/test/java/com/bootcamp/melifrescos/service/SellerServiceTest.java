package com.bootcamp.melifrescos.service;

import static org.assertj.core.api.Assertions.assertThat;
import com.bootcamp.melifrescos.model.Seller;
import com.bootcamp.melifrescos.repository.ISellerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class SellerServiceTest {

    @InjectMocks
    private SellerService sellerService;

    @Mock
    private ISellerRepo repo;

    private Seller seller;

    @BeforeEach
    void setUp() {
        seller = new Seller(1L, "Maria", "1140028922", "mariazinha@email.com", "62.485.513/0001-97", null);
    }

    @Test
    void create_returnSeller_whenCaseOfSuccess() {
        Mockito.when(repo.save(ArgumentMatchers.any()))
                .thenReturn(seller);

        Seller newSeller = sellerService.create(seller);

        assertThat(seller.getCnpj()).isEqualTo(newSeller.getCnpj());
        assertThat(seller.getEmail()).isEqualTo(newSeller.getEmail());
        assertThat(seller.getPhoneNumber()).isEqualTo(newSeller.getPhoneNumber());
    }

    @Test
    void getById_returnSeller_whenIdIsValid() {
        Mockito.when(repo.findById(ArgumentMatchers.any()))
                .thenReturn(Optional.of(seller));

        Seller getSeller = sellerService.getById(1L);

        assertThat(seller.getName()).isEqualTo(getSeller.getName());
        assertThat(seller.getEmail()).isEqualTo(getSeller.getEmail());
        assertThat(seller.getCnpj()).isEqualTo(getSeller.getCnpj());
    }
}
