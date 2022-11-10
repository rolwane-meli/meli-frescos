package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.interfaces.ISectorService;
import com.bootcamp.melifrescos.model.Sector;
import com.bootcamp.melifrescos.repository.ISectorRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SectorService implements ISectorService {

    private final ISectorRepo repo;

    @Override
    public Sector create(Sector sector) {
        return repo.save(sector);
    }

    public Optional<Sector> getById(Long id){
        return repo.findById(id);
    }
}
