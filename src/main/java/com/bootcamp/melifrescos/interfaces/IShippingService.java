package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.dto.ShippingResponseDTO;

public interface IShippingService {
    ShippingResponseDTO getShippingPrice(String cep, Long productId, int quantity);
}
