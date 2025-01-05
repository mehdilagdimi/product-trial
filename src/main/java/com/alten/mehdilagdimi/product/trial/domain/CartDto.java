package com.alten.mehdilagdimi.product.trial.domain;


import java.util.Set;

public record CartDto(
        Long id,
        Long userId,
        String email,
        Set<ItemDto> items) {}

