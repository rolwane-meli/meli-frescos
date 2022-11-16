package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.model.Buyer;

import java.util.Optional;

public interface IBuyerService {
    Optional<Buyer> getById(Long id);
}
