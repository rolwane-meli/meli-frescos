package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.model.Buyer;
import com.bootcamp.melifrescos.model.PurchaseOrder;
import com.bootcamp.melifrescos.repository.IBuyerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class BuyerServiceTest {

    @InjectMocks
    BuyerService service;

    @Mock
    IBuyerRepo repo;

    private Buyer buyer;

    @BeforeEach
    void setUP() {
        buyer = new Buyer(1L, "name", "123.456.789-00", "name@email.com", Arrays.asList(new PurchaseOrder()));
    }

    @Test
    public void getById_returnBuyer_whenIdSuccess() {
        Mockito.when(repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(buyer));
        Optional<Buyer> response = service.getById(1L);
        assertThat(response.get().getId()).isEqualTo(buyer.getId());
        assertThat(response.get().getName()).isEqualTo(buyer.getName());
        assertThat(response.get().getEmail()).isEqualTo(buyer.getEmail());
        assertThat(response.get().getPurchaseOrders()).isEqualTo(buyer.getPurchaseOrders());
    }

    @Test
    public void getById_returnNotFoundException_whenIdFalid() {
        Mockito.when(repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getById(1L));
    }
}

