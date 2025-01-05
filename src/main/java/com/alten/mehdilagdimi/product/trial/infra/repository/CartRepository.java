package com.alten.mehdilagdimi.product.trial.infra.repository;

import com.alten.mehdilagdimi.product.trial.domain.Cart;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CartRepository extends ListCrudRepository<Cart, Long> {
    Optional<Cart> findByUserEmail(String userEmail);
}
