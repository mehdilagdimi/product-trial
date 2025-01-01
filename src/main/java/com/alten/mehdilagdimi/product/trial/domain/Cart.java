package com.alten.mehdilagdimi.product.trial.domain;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public record Cart(
        @Id @GeneratedValue
        Long id,
        @ManyToOne
        UserAccount user,
        @OneToMany
        Set<Product> productList,
        Boolean isActive){}

