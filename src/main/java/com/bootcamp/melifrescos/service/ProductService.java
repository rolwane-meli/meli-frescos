package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.ProductRequestDTO;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.interfaces.IProductService;
import com.bootcamp.melifrescos.model.Product;
import com.bootcamp.melifrescos.model.Seller;
import com.bootcamp.melifrescos.repository.IProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

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

        Product newProduct = new Product(null, product.getName(), Type.fromValue(product.getType()), seller,null);
        return repo.save(newProduct);
    }
}
