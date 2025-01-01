package com.alten.mehdilagdimi.product.trial.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
public record UserAccount(

        @Id @GeneratedValue
        Long id,
        String username,
        String firstname,
        @Column(unique = true)
        String email,
        String password,
        @OneToMany(mappedBy = "user")
        List<Cart> cart,
        @OneToOne(mappedBy = "user")
        WishList wishList) {
}

