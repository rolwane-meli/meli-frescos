package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.interfaces.IBatchService;
import com.bootcamp.melifrescos.model.Batch;
import com.bootcamp.melifrescos.repository.IBatchRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatchService implements IBatchService {

    private final IBatchRepo repo;

    @Override
    public Batch create(Batch batch) {
        return repo.save(batch);
    }
}
