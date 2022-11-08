package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.interfaces.IRepresentativeService;
import com.bootcamp.melifrescos.model.Representative;
import com.bootcamp.melifrescos.repository.IRepresentativeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepresentativeService implements IRepresentativeService {

    private final IRepresentativeRepo repo;

    @Override
    public Representative create(Representative representative) {
        return repo.save(representative);
    }
}
