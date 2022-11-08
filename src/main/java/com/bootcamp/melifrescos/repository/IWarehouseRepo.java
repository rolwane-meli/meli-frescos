package com.bootcamp.melifrescos.repository;

import com.bootcamp.melifrescos.model.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWarehouseRepo extends JpaRepository<Warehouse, Long> {
}
