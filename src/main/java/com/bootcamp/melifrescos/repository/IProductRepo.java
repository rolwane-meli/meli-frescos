package com.bootcamp.melifrescos.repository;

import com.bootcamp.melifrescos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepo extends JpaRepository<Product, Long> {
}
