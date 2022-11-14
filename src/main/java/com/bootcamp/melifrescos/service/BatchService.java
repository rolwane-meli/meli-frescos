package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.BatchDTO;
import com.bootcamp.melifrescos.exceptions.BatchNotExistException;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.interfaces.IBatchService;
import com.bootcamp.melifrescos.interfaces.IProductService;
import com.bootcamp.melifrescos.model.Batch;
import com.bootcamp.melifrescos.model.InboundOrder;
import com.bootcamp.melifrescos.model.Product;
import com.bootcamp.melifrescos.repository.IBatchRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BatchService implements IBatchService {

    private final IBatchRepo repo;
    private final IProductService productService;

    /**
     * Método responsavel por criar um lote
     * @param batchDTO BatchDTO
     * @return retorna lote criado
     */
    @Override
    public Batch create(BatchDTO batchDTO) {
        Optional<Product> product = this.productService.getById(batchDTO.getProductId());

        if (batchDTO.getProductId()==null || product.isEmpty()) {
            throw new RuntimeException("Produto inválido");
        }

        Batch batch = new Batch();
        BeanUtils.copyProperties(batchDTO, batch);
        batch.setProduct(product.get());

        return repo.save(batch);
    }

    /**
     * Método responsavel por criar lotes atraves de uma lista
     * @param batchDTOS lista de Lotes
     * @return retorna os lotes criados
     */
    @Override
    public List<Batch> createAll(List<BatchDTO> batchDTOS, InboundOrder savedInboundorder){
        List<Batch> batches = new ArrayList<>();
        batchDTOS.stream().forEach(b -> {
            Optional<Product> product = this.productService.getById(b.getProductId());

            if (b.getProductId()==null || product.isEmpty()) {
                throw new RuntimeException("Lista de lotes, possuí um produto inválido");
            }

            Batch batch = new Batch();
            BeanUtils.copyProperties(b, batch);
            batch.setProduct(product.get());
            batch.setInboundOrder(savedInboundorder);

            batches.add(batch);
        });

        return repo.saveAll(batches);
    }

    @Override
    public Optional<Batch> getById(Long id) {
        Optional<Batch> batchOptional = repo.findById(id);

        if(batchOptional.isEmpty()){
            throw new NotFoundException("Lote não encontrado");
        }

        return repo.findById(id);
    }

    @Override
    public Boolean productMatchBatch(Long idBatch, Long idProduct) {
        Optional<Batch> batch = getById(idBatch);
        if(batch.get().getProduct().getId().equals(idProduct)) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean quantityProductMatchBatch(Long idBatch, int quantityProduct) {
        Optional<Batch> batch = getById(idBatch);
        if(batch.get().getProductQuantity() >= quantityProduct) {
            return true;
        }
        return false;
    }
}
