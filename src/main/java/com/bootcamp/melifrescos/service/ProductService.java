package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.*;
import com.bootcamp.melifrescos.enums.Type;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.interfaces.IProductService;
import com.bootcamp.melifrescos.model.Product;
import com.bootcamp.melifrescos.model.Seller;
import com.bootcamp.melifrescos.repository.IProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepo repo;
    private final SellerService sellerService;
    private final RestTemplate restTemplate;


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
     * Método responsável por buscar todos os produtos alocados em algum lote/armazém
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
     * Método responsável por listar todos os produtos com o preço convertido conforme a moeda desejada
     * @param currency Moeda a converter
     * @return Lista de produtos com preço convertido
     */
    @Override
    public List<ProductConvertedDTO> getAllProductsWithConvertedPrice(String currency) {
        var price = BigDecimal.valueOf(requestCurrencyApi(currency).getRate());

        List<ProductListDTO> products = repo.findProductsByBatches();

        return this.convertProductListDtoToProductConvertedDTO(products,currency,price);
    }

    /**
     * Método responsável por buscar um determinado produto por ‘id’ e converter o preço conforme a moeda desejada
     * @param id id do produto
     * @param currency Moeda a converter
     * @return lista com o produto
     */
    public List<ProductConvertedDTO> getByProductIdWithConvertedPrice(Long id, String currency){
        var price = BigDecimal.valueOf(requestCurrencyApi(currency).getRate());

        List<ProductListDTO> products = repo.findProductsByBatches();

        List<ProductConvertedDTO> productConvertedDTO = this.convertProductListDtoToProductConvertedDTO(products, currency, price);

        List<ProductConvertedDTO> filteredProducts = filterProductConvertedDtoById(productConvertedDTO,id);

        if (filteredProducts.isEmpty()){
            throw new NotFoundException("Produto nao existe");
        }

        return filteredProducts;
    }

    /**
     * Método responsável por converter ProductListDto em ProductListBatchDto
     * @param products lista de ProductListDTO
     * @param currency moeda
     * @param price preço da moeda
     * @return Lista de ProductConvertedDTO
     */
    private List<ProductConvertedDTO> convertProductListDtoToProductConvertedDTO(List<ProductListDTO> products, String currency, BigDecimal price){
        List<ProductConvertedDTO> productConvertedDtoList = new ArrayList<>();

        products.forEach(el -> {
            productConvertedDtoList.add(new ProductConvertedDTO(el,currency,price));
        });

        return productConvertedDtoList;
    }

    /**
     * Método responsável por filtrar um produto conforme o ‘id’
     * @param productConvertedDTO lista de productConvertedDTO
     * @param id id do produto
     * @return lista de productConvertedDTO com o produto filtrado
     */
    private List<ProductConvertedDTO> filterProductConvertedDtoById(List<ProductConvertedDTO> productConvertedDTO, Long id) {
        return productConvertedDTO.stream()
                .filter(el -> el.getId().equals(id))
                .collect(Collectors.toList());
    }

    /**
     * Método responsável por requisitar api de conversão de moedas
     * @param currency moeda a buscar
     * @return CurrencyDTO
     */
    private CurrencyDTO requestCurrencyApi(String currency) {
        CurrencyDTO response;

        try{
            response = this.restTemplate.getForEntity("https://api.mercadolibre.com/currency_conversions/search?from=BRL&to=" + currency, CurrencyDTO.class).getBody();
        } catch (HttpClientErrorException.BadRequest ex){
            throw new NotFoundException("Essa moeda ainda nao existe");
        };

        return response;
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
