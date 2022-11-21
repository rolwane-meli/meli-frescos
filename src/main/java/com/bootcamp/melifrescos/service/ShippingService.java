package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.CepDTO;
import com.bootcamp.melifrescos.dto.ProductShippingDTO;
import com.bootcamp.melifrescos.dto.ShippingResponseDTO;
import com.bootcamp.melifrescos.exceptions.InvalidCepException;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.interfaces.IProductService;
import com.bootcamp.melifrescos.interfaces.IShippingService;
import com.bootcamp.melifrescos.model.Product;
import com.bootcamp.melifrescos.model.Shipping;
import com.bootcamp.melifrescos.repository.IShippingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShippingService implements IShippingService {
    private final IShippingRepo repo;

    private final IProductService productService;

    private final RestTemplate restTemplate;

    @Override
    public ShippingResponseDTO getShippingPrice(String cep, Long productId, int quantity) {
        Optional<Product> product = productService.getById(productId);

        if (product.isEmpty()) throw new NotFoundException("O produto informado não existe");

        CepDTO cepDTO = fetchCepDTO(cep);

        ShippingResponseDTO shippingResponseDTO = new ShippingResponseDTO(
            cep,
            cepDTO.getUf(),
            cepDTO.getLocalidade(),
            new BigDecimal("0.00"),
            new ProductShippingDTO(product.get(), quantity)
        );

        if (quantity < 10) {
            Shipping shipping = repo.findShippingByUf(cepDTO.getUf());
            shippingResponseDTO.setShippingPrice(shipping.getShippingPrice());
        }

        return shippingResponseDTO;
    }

    private CepDTO fetchCepDTO(String cep) {
        try {
            CepDTO cepDTO = restTemplate.getForEntity("https://viacep.com.br/ws/"+cep+"/json/", CepDTO.class).getBody();

            if (cepDTO.getCep() == null) {
                throw new NotFoundException("O CEP informado não foi encontrado");
            }

            return cepDTO;
        } catch (HttpClientErrorException ex) {
            throw new InvalidCepException("O CEP informado é inválido");
        }
    }
}
