package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.InboundOrderDTO;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.interfaces.IInboundOrderService;
import com.bootcamp.melifrescos.model.Batch;
import com.bootcamp.melifrescos.model.InboundOrder;
import com.bootcamp.melifrescos.model.Sector;
import com.bootcamp.melifrescos.repository.IInboundOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InboundOrderService implements IInboundOrderService {

    private final IInboundOrderRepo repo;
    private final SectorService sectorService;

    @Override
    public InboundOrder create(InboundOrderDTO inboundOrder) {
        Optional<Sector> sector = sectorService.getById(inboundOrder.getSectionCode());

        if (sector.isEmpty()){
            throw new RuntimeException("setor nao existe");
        }

        if (!this.hasVolume(inboundOrder.getBatchStock(),sector.get())){
            throw new RuntimeException("Nao tem capacidade para esse volume");
        }

        if (this.getTypeSector(inboundOrder.getBatchStock()) != sector.get().getType()) {
            throw new RuntimeException("Setor de  armazemento errado");
        }

        InboundOrder inboundOrder1 = new InboundOrder(null,11,inboundOrder.getOrderDate(),sector.get(),inboundOrder.getBatchStock());
        return repo.save(inboundOrder1);
    }

    public Type getTypeSector(List<Batch> batches){
        int contFrozen = 0;
        int contRefrigerated = 0;
        int contFresh = 0;

        for (Batch batch: batches) {
            if (batch.getCurrentTemperature() < 0) {
                contFrozen++;
            } else if (batch.getCurrentTemperature() <=10) {
                contRefrigerated++;
            } else {
                contFresh ++;
            }
        }

        if (contFrozen == batches.size()){
            return Type.FROZEN;
        }
        if (contRefrigerated == batches.size()){
            return Type.REFRIGERATED;
        }
        if (contFresh == batches.size()){
            return Type.REFRIGERATED;
        }

        return null;
    }

    public Boolean hasVolume(List<Batch> batches, Sector sector) {

        double total = batches
                .stream()
                .mapToDouble(Batch::getVolume).sum();

        return sector.getCapacity() - total  >= 0;
    }
}
