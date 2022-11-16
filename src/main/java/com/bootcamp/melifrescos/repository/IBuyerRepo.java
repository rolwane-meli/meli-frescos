package com.bootcamp.melifrescos.repository;

import com.bootcamp.melifrescos.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBuyerRepo extends JpaRepository<Buyer, Long> {
}
