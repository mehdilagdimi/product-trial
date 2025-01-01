package com.alten.mehdilagdimi.product.trial.infra.repository;

import com.alten.mehdilagdimi.product.trial.domain.Cart;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends ListCrudRepository<Cart, Long> {
    Cart findByUserEmailAndIsActive(String userEmail, Boolean isActive);
}
