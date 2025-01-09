package com.alten.mehdilagdimi.product.trial.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AuthResp(
        @NotNull String username,
        @NotNull String firstname,
        String email,
        @NotNull String token,
        @NotNull long expires_in) {}

