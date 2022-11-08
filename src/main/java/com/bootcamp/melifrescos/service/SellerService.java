package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.interfaces.ISellerService;
import com.bootcamp.melifrescos.model.Seller;
import com.bootcamp.melifrescos.repository.ISellerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerService implements ISellerService {

    private final ISellerRepo repo;

    @Override
    public Seller create(Seller seller) {
        return repo.save(seller);
    }
}
