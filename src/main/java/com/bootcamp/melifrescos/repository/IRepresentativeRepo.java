package com.bootcamp.melifrescos.repository;

import com.bootcamp.melifrescos.model.Representative;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepresentativeRepo extends JpaRepository<Representative, Long> {
}
