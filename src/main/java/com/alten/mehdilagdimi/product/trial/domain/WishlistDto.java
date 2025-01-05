package com.alten.mehdilagdimi.product.trial.domain;


import java.util.List;

public record WishlistDto(
        Long id,
        Long userId,
        String email,
        List<ProductDtoResp> products) {}

