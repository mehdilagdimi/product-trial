package com.alten.mehdilagdimi.product.trial.business.service;

import com.alten.mehdilagdimi.product.trial.business.exception.ProductNotFoundException;
import com.alten.mehdilagdimi.product.trial.domain.Product;
import com.alten.mehdilagdimi.product.trial.domain.ProductDtoReq;
import com.alten.mehdilagdimi.product.trial.domain.ProductDtoResp;
import com.alten.mehdilagdimi.product.trial.infra.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository repository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository repository, ProductMapper productMapper){
        this.repository = repository;
        this.productMapper = productMapper;
    }

    public List<ProductDtoResp> getAll(){
        List<Product> products = repository.findAll();
        return products
                .stream()
                .map( p -> productMapper.toDto(p))
                .toList();
    }

    public ProductDtoResp getById(Long id) {
        Product product =
                    repository
                    .findById(id)
                    .orElseThrow(() -> new ProductNotFoundException(id));
        return productMapper.toDto(product);
    }

    public ProductDtoResp save(ProductDtoReq product) {
        Product p = repository
                    .save(productMapper.toEntity(product));
        return productMapper.toDto(p);
    }

    public ProductDtoResp update(Long id, ProductDtoReq product) {
        if(null != id && doesExist(id)) {
            logger.info("Proceeding with update...", id);
            Product p = repository
                        .save(productMapper.toEntity(product, id));
            return productMapper.toDto(p);
        } else {
            logger.warn("Update not possible...", id);
            throw new UnsupportedOperationException("Creation of entity through through PATCH is not allowed");
        }
    }


    public void deleteById(Long id) {
        repository
                    .deleteById(id);
        logger.info("Product has been deleted, ID : {}", id);
    }

    private boolean doesExist(Long id){
        if(repository.existsById(id)){
            logger.info("Product with ID : {} exists:");
            return true;
        } else {
            logger.info("Product with ID : {} does not exist:");
            return false;
        }
    }


//    private ProductDto mapToProductDto(Product p){
//        return new ProductDto(
//                p.code(),
//                p.name(),
//                p.description(),
//                p.image(),
//                p.category(),
//                p.price(),
//                p.quantity(),
//                p.internalReference(),
//                p.shellId(),
//                p.inventoryStatus(),
//                p.rating(),
//                p.createdAt(),
//                p.updatedAt()).
//    }

}
