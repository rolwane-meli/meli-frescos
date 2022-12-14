package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.BatchListDTO;
import com.bootcamp.melifrescos.dto.ProductWithBatchesDTO;
import com.bootcamp.melifrescos.dto.ProductListDTO;
import com.bootcamp.melifrescos.dto.ProductRequestDTO;
import com.bootcamp.melifrescos.dto.ProductResponseDTO;
import com.bootcamp.melifrescos.dto.PurchaseOrderResponse;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.interfaces.IProductService;
import com.bootcamp.melifrescos.model.Product;
import com.bootcamp.melifrescos.model.Seller;
import com.bootcamp.melifrescos.repository.IProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepo repo;
    private final SellerService sellerService;

    /**
     * Método responsável por criar um produto
     * @param product ProductRequestDTO
     * @return Novo Produto
     */
    @Override
    public Product create(ProductRequestDTO product) {
        Seller seller = this.sellerService.getById(product.getSellerId());

        if (seller == null) throw new NotFoundException("Vendedor nao existe");

        Product newProduct = new Product(null, product.getName(), Type.fromValue(product.getType()), seller, null, null);
        return repo.save(newProduct);
    }

    /**
     * Método responsável por buscar um Produto pelo Id
     * @param id id do produto
     * @return retorna um Produto
     */
    @Override
    public Optional<Product> getById(Long id) {
        return repo.findById(id);
    }

    /**
     * Método responsável por buscar todos os produtos que estão alocados em algum lote/armazém
     * @return returna uma lista de produtos, com seu preço e Id do lote
     */
    @Override
    public List<ProductListDTO> findProductsByBatches(){
        return repo.findProductsByBatches();
    }

    /**
     * Método responsável por buscar os produtos ques estão alocados em algum lote/armazém e filtrar pelo seu tipo
     * @param type Tipo dos produtos que deseja buscar
     * @return Lista de produtos filtrada, com seu preço e Id do lote
     */
    @Override
    public List<ProductListDTO> findProductsByBatchesAndType(Type type){
        return repo.findProductsByBatchesAndType(type);
    }

    /**
     * Método responsável por buscar um produto com seus lotes
     * @param id id do produto
     * @return ProductWhitBatchesDTO = produto com sua lista de lotes
     */
    public ProductWithBatchesDTO getByIdWithBatches(Long id){
        Optional<Product> product = repo.findById(id);

        if (product.isEmpty()) throw new NotFoundException("Produto Não existe");

        return new ProductWithBatchesDTO(product.get());
    }

    /**
     * Método responsável por ordenar a lista de lotes do produto conforme o parmetro passado
     * @param id id do produto
     * @param type tipo da filtragem
     * @return productWithBatchesDTO = produto com a lista de lotes ordenada
     */
    public ProductWithBatchesDTO getByIdWithSortedBatches(Long id, String type){

        ProductWithBatchesDTO productWithBatchesDTO = this.getByIdWithBatches(id);

        List<BatchListDTO> batchListDTO = productWithBatchesDTO.getBatchStock();

        switch (type.toUpperCase()) {
            case "L":
                productWithBatchesDTO.setBatchStock(batchListDTO);
                break;
            case "Q":
                productWithBatchesDTO.setBatchStock(orderByAmount(batchListDTO));
                break;
            case "V":
                productWithBatchesDTO.setBatchStock(orderByDueDate(batchListDTO));
                break;
            default:
                throw new NotFoundException("Tipo inexistente");
        }

        return productWithBatchesDTO;
    }

    /**
     * Método responsável por ordenar os lotes conforme a quantidade
     * @param batches - listas de lotes
     * @return batches - lista de lotes ordenado por quantidade
     */
    private List<BatchListDTO> orderByAmount(List<BatchListDTO> batches) {
        return batches.stream()
                .sorted(Comparator.comparingInt(BatchListDTO::getCurrentQuantity))
                .collect(Collectors.toList());
    }

    /**
     * Métodos responsável por ordenar os lotes conforme a data de validade
     * @param batches - lista de lotes
     * @return batches - lista de lotes ordenado por data de validade
     */
    private List<BatchListDTO> orderByDueDate(List<BatchListDTO> batches) {
        return batches.stream()
                .sorted(Comparator.comparing(BatchListDTO::getDueDate))
                .collect(Collectors.toList());
    }
}
