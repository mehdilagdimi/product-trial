package com.alten.mehdilagdimi.product.trial.domain;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public record WishList(
        @Id @GeneratedValue
        Long id,
        @OneToOne
        UserAccount user,
        @OneToMany
        Set<Product> productList){}

