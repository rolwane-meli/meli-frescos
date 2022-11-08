package com.bootcamp.melifrescos.repository;

import com.bootcamp.melifrescos.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBatchRepo extends JpaRepository<Batch, Long> {
}
