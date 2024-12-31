package com.alten.mehdilagdimi.product.trial.infra.repository;

import com.alten.mehdilagdimi.product.trial.domain.UserAccount;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends ListCrudRepository<UserAccount, Long> {
    Optional<UserAccount> findByEmailAndPassword(String email, String password);
    Optional<UserAccount> findByEmail(String email);
}
