package com.bootcamp.melifrescos.repository;

import com.bootcamp.melifrescos.model.Sector;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISectorRepo extends JpaRepository<Sector, Long> {
}
