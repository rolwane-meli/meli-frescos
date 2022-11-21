package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.CepDTO;
import com.bootcamp.melifrescos.dto.ProductShippingDTO;
import com.bootcamp.melifrescos.dto.ShippingResponseDTO;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.exceptions.InvalidCepException;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.model.Product;
import com.bootcamp.melifrescos.model.Shipping;
import com.bootcamp.melifrescos.repository.IShippingRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ShippingServiceTest {

    @InjectMocks
    private ShippingService service;

    @Mock
    private ProductService productService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private IShippingRepo repo;

    private Product product;

    private Shipping shipping;

    private ShippingResponseDTO shippingResponseDTO;

    private CepDTO cepDTO;

    @BeforeEach
    public void setup() {
        product = new Product(1L, "Leite", Type.REFRIGERATED);
        shipping = new Shipping(1L, "CE", new BigDecimal("50.00"));
        shippingResponseDTO = new ShippingResponseDTO("62600000", "CE", "Itapajé", new BigDecimal("0.00"), new ProductShippingDTO(product, 10));
        cepDTO = new CepDTO("62600000", "", "", "", "Itapajé", "CE", "2306306", "", "85", "1427");
    }

    @Test
    public void getShippingPrice_givenCepProductIdAndQuantity_returnsShippingResponseDTO() {
        Mockito.when(productService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(product));

        Mockito.when(restTemplate.getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(cepDTO, HttpStatus.OK));

        ShippingResponseDTO response = service.getShippingPrice("62600000", 1L, 10);

        assertThat(response.getCep()).isEqualTo(shippingResponseDTO.getCep());
        assertThat(response.getLocality()).isEqualTo(shippingResponseDTO.getLocality());
    }

    @Test
    public void getShippingPrice_givenQuantityLessThen10_returnsShippingResponseDTOWithPriceGreaterThenZero() {
        Mockito.when(productService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(product));

        Mockito.when(restTemplate.getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(cepDTO, HttpStatus.OK));

        Mockito.when(repo.findShippingByUf(ArgumentMatchers.anyString()))
                .thenReturn(shipping);

        ShippingResponseDTO response = service.getShippingPrice("62600000", 1L, 5);

        assertThat(response.getShippingPrice()).isEqualTo("50.00");
    }

    @Test
    public void getShippingPrice_givenAInvalidCep_throwsInvalidCepException() {
        Mockito.when(productService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(product));

        Mockito.when(restTemplate.getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                        .thenThrow(HttpClientErrorException.class);

        assertThrows(InvalidCepException.class, () -> {
            service.getShippingPrice("AAA", 1L, 5);
        });
    }

    @Test
    public void getShippingPrice_givenANonexistentCep_throwsNotFoundException() {
        Mockito.when(productService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(product));

        Mockito.when(restTemplate.getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.any()))
                .thenReturn(new ResponseEntity<>(new CepDTO(), HttpStatus.OK));

        assertThrows(NotFoundException.class, () -> {
            service.getShippingPrice("00000000", 1L, 5);
        });
    }
}
