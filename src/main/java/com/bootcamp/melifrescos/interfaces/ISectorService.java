package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.dto.SectorRequestDTO;
import com.bootcamp.melifrescos.model.Sector;

import java.util.Optional;

public interface ISectorService {

    Sector create(SectorRequestDTO sector);

     Optional<Sector> getById(Long id);
}
