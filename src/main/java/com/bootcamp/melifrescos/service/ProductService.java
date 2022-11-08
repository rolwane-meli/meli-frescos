package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.interfaces.IProductService;
import com.bootcamp.melifrescos.model.Product;
import com.bootcamp.melifrescos.repository.IProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepo repo;

    @Override
    public Product create(Product product) {
        return repo.save(product);
    }
}
