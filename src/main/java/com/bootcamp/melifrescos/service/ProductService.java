package com.bootcamp.melifrescos.service;

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

import java.util.List;
import java.util.Optional;

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
        if (seller == null) {
            // TODO: trocar a exception  para uma personalizada
            throw new RuntimeException("Vendedor nao existe");
        }

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
        Optional<Product> product = repo.findById(id);
        if(product.isEmpty()) { throw new NotFoundException("Produto não encontrado."); }
        return product;
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
}
