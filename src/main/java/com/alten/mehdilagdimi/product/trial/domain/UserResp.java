package com.alten.mehdilagdimi.product.trial.domain;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResp(
        Long id,
        @Email String email,
        String username,
        String firstname) {}

