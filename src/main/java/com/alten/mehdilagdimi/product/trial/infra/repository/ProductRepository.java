package com.alten.mehdilagdimi.product.trial.infra.repository;

import com.alten.mehdilagdimi.product.trial.domain.Product;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends ListCrudRepository<Product, Long> {
    Optional<String> findImageById(Long id);
}
