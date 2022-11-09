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

    /**
     * Método responsável por buscar um vendedor por id
     * @param id Id do vendedor
     * @return Vendedor
     */
    @Override
    public Seller getById(Long id) {
        return repo.findById(id).orElse(null);
    }
}
