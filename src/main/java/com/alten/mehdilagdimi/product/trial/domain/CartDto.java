package com.alten.mehdilagdimi.product.trial.domain;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CartDto(
        Long id,
        Long userId,
        String email,
        Set<ItemDto> items) {}

