package com.alten.mehdilagdimi.product.trial.domain;


import java.util.List;

public record CartDto(
        Long id,
        String userId,
        String email,
        List<Item> items) {}

