package com.alten.mehdilagdimi.product.trial.domain;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record UserReq(
        @NotNull @Email String email,
        @NotNull String username,
        String firstname,
        @NotNull String password) {}

