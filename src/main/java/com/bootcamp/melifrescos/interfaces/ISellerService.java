package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.model.Seller;

public interface ISellerService {
    public Seller create(Seller seller);
    public Seller getById(Long id);
}
