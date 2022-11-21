package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.interfaces.IRepresentativeService;
import com.bootcamp.melifrescos.model.Representative;
import com.bootcamp.melifrescos.repository.IRepresentativeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepresentativeService implements IRepresentativeService {

    private final IRepresentativeRepo repo;

    /**
     * Método reponsável por criar Representative
     * @param representative via body
     * @return Representative
     */
    @Override
    public Representative create(Representative representative) {
        return repo.save(representative);
    }

    /**
     * método responsável por buscar Representative
     * @param id via requisicao
     * @return Optinal de Representative
     */
    @Override
    public Optional<Representative> getById(Long id) {
        return repo.findById(id);
    }
}
