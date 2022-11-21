package com.bootcamp.melifrescos.repository;

import com.bootcamp.melifrescos.model.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IShippingRepo extends JpaRepository<Shipping, Long> {
    Shipping findShippingByUf(String uf);
}
