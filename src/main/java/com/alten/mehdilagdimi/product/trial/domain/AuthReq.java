package com.alten.mehdilagdimi.product.trial.domain;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AuthReq(
        @NotNull @Email String email,
        @NotNull String password) {}

