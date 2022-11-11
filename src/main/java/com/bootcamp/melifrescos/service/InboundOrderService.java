package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.BatchDTO;
import com.bootcamp.melifrescos.dto.InboundOrderDTO;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.exceptions.InvalidEnumTypeException;
import com.bootcamp.melifrescos.exceptions.InvalidSectorTypeException;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.exceptions.UnavailableVolumeException;
import com.bootcamp.melifrescos.interfaces.IInboundOrderService;
import com.bootcamp.melifrescos.model.InboundOrder;
import com.bootcamp.melifrescos.model.Sector;
import com.bootcamp.melifrescos.repository.IInboundOrderRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InboundOrderService implements IInboundOrderService {

    private final IInboundOrderRepo repo;
    private final SectorService sectorService;
    private final BatchService batchService;

    /**
     * Método responsável por criar uma ordem de compra
     * @param inboundOrderDTO ordem de compra
     * @return onderm de compra criada
     */
    @Transactional
    @Override
    public InboundOrder create(InboundOrderDTO inboundOrderDTO) {
        Optional<Sector> sector = sectorService.getById(inboundOrderDTO.getSectionCode());

        this.validateInboundOrder(inboundOrderDTO, sector);

        InboundOrder newInboundOrder = new InboundOrder(null,inboundOrderDTO.getOrderDate(),sector.get(),null);
        InboundOrder inboundorder = repo.save(newInboundOrder);

        batchService.createAll(inboundOrderDTO.getBatchStock(),inboundorder);

        return inboundorder;
    }

    /**
     * Método responsável por atualizar uma inboundOrder
     * @param id inboundOrder a ser atualizada
     * @param inboundOrderDTO
     * @return InboundOrder atualizada
     */
    public InboundOrder update(Long id, InboundOrderDTO inboundOrderDTO) {
        Optional<InboundOrder> inboundOrder = this.getById(id);

        if (inboundOrder.isEmpty()) {
            throw new NotFoundException("inboundOrder não existe");
        }

        Optional<Sector> sector = sectorService.getById(inboundOrderDTO.getSectionCode());

        this.validateInboundOrder(inboundOrderDTO, sector);

        InboundOrder newInboundOrder = new InboundOrder(id, inboundOrderDTO.getOrderDate(), sector.get(),null);
        InboundOrder savedInboundOrder = repo.save(newInboundOrder);

        batchService.createAll(inboundOrderDTO.getBatchStock(), savedInboundOrder);

        return savedInboundOrder;
    }

    public Optional<InboundOrder> getById(Long id) {
        return repo.findById(id);
    }

    /**
     * Método responsável por validar a ordem de compra
     * @param inboundOrder ordem de compra
     * @param sector setor
     */
    private void validateInboundOrder(InboundOrderDTO inboundOrder, Optional<Sector> sector){
        if (sector.isEmpty()){
            throw new NotFoundException("setor nao existe");
        }

        if (!this.hasVolume(inboundOrder.getBatchStock(),sector.get())){
            throw new UnavailableVolumeException("Nao tem capacidade para esse volume");
        }

        if (this.getTypeSector(inboundOrder.getBatchStock()) != sector.get().getType()) {
            throw new InvalidSectorTypeException("Setor de armazemento errado");
        }
    }

    /**
     * Método responsável por validar se o tipo dos  lotes  sao iguais
     * @param batches Lista de lotes
     * @return tipo
     */
    private Type getTypeSector(List<BatchDTO> batches){

        Type type = getTypeFromTemperature(batches.get(0).getCurrentTemperature());

        for (BatchDTO batch: batches) {
            if (getTypeFromTemperature(batch.getCurrentTemperature()) != type) {
                return null;
            }
        }

        return type;
    }

    /**
     * Método responsável por pegar o tipo através da temperatura
     * @param temperature
     * @return tipo congelado ou resfriado ou fresco
     */
    private Type getTypeFromTemperature(double temperature){
        if (temperature < 0) {
            return Type.FROZEN;
        }
        if (temperature <=10) {
            return Type.REFRIGERATED;
        }
        if (temperature <=20) {
            return Type.FRESH;
        }

        throw new InvalidEnumTypeException("Tipo invalido");
    }

    /**
     * Método responsável por validar se tem volume disponível no setor
     * @param batches Lista de lotes
     * @param sector setor
     * @return boolean
     */
    private Boolean hasVolume(List<BatchDTO> batches, Sector sector) {

        double total = batches
                .stream()
                .mapToDouble(BatchDTO::getVolume).sum();

        return sector.getCapacity() - total  >= 0;
    }
}
