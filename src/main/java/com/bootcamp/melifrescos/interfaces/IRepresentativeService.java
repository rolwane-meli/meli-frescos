package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.model.Representative;

import java.util.Optional;

public interface IRepresentativeService {
    Representative create(Representative representative);
    Optional<Representative> getById(Long id);
}
