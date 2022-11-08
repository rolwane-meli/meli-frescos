package com.bootcamp.melifrescos.repository;

import com.bootcamp.melifrescos.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISellerRepo extends JpaRepository<Seller, Long> {

}
