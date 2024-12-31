package com.alten.mehdilagdimi.product.trial.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public record UserAccount(

        @Id @GeneratedValue
        Long id,
        String username,
        String firstname,
        @Column(unique = true)
        String email,
        String password) {
}

