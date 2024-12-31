package com.alten.mehdilagdimi.product.trial.domain;


import jakarta.validation.constraints.NotNull;

public record AuthResp(
        @NotNull String username,
        @NotNull String firstname,
        String email,
        @NotNull String token,
        @NotNull long expires_in) {}

