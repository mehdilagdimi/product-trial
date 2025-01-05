package com.alten.mehdilagdimi.product.trial.domain;


import java.util.List;

public record WishlistDto(
        Long id,
        String userId,
        String email,
        List<Product> products) {}

