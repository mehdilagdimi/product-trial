package com.alten.mehdilagdimi.product.trial.infra.repository;

import com.alten.mehdilagdimi.product.trial.domain.WishList;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishListRepository extends ListCrudRepository<WishList, Long> {
    WishList findByUserEmail(String userEmail);
}
