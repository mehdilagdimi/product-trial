package com.alten.mehdilagdimi.product.trial.domain;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record WishlistDto(
        Long id,
        Long userId,
        String email,
        List<ProductDtoResp> products) {}

