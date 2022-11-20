package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.interfaces.IBuyerService;
import com.bootcamp.melifrescos.model.Buyer;
import com.bootcamp.melifrescos.repository.IBuyerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuyerService implements IBuyerService {
    private final IBuyerRepo repo;

    /**
     * Método responsável por buscar Buyer por ID
     * @param id
     * @return Buyer
     */
    @Override
    public Optional<Buyer> getById(Long id){
        Optional<Buyer> buyer = repo.findById(id);
        if(buyer.isEmpty()) { throw new NotFoundException("Comprador não encontrado."); }
        return buyer;
    }
}
