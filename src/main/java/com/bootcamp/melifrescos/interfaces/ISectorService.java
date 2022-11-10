package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.model.Sector;

import java.util.Optional;

public interface ISectorService {
    public Sector create(Sector sector);

    public Optional<Sector> getById(Long id);

    public boolean isWarehouseExists(Long id);
}
