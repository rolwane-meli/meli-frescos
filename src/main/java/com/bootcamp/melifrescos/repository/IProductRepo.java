package com.bootcamp.melifrescos.repository;

import com.bootcamp.melifrescos.dto.ProductListDTO;
import com.bootcamp.melifrescos.dto.PurchaseOrderResponse;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IProductRepo extends JpaRepository<Product, Long> {

    @Query("select new com.bootcamp.melifrescos.dto.ProductListDTO(p.id, p.name, p.type, b.price, b.productQuantity, b.id) from Product p inner join Batch b on (b.product.id=p.id)")
    List<ProductListDTO> findProductsByBatches();

    @Query("select new com.bootcamp.melifrescos.dto.ProductListDTO(p.id, p.name, p.type, b.price, b.productQuantity, b.id) from Product p inner join Batch b on (b.product.id=p.id) where p.type=:type")
    List<ProductListDTO> findProductsByBatchesAndType(@Param("type") Type type);
}
