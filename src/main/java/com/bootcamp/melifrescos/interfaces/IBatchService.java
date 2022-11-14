package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.dto.BatchDTO;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.exceptions.BatchNotExistException;
import com.bootcamp.melifrescos.model.Batch;
import com.bootcamp.melifrescos.model.InboundOrder;

import java.util.List;
import java.util.Optional;

public interface IBatchService {
    Batch create(BatchDTO batch);

    List<Batch> createAll(List<BatchDTO> batches, InboundOrder inboundOrder);

    Optional<Batch> getById(Long id);

    List<BatchDTO> getBatchesBySector(Long sectorId, int days);

    List<BatchDTO> getAllByDueDateAndCategory(int days, Type type);
}
