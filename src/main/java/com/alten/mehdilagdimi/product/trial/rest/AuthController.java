package com.alten.mehdilagdimi.product.trial.rest;

import com.alten.mehdilagdimi.product.trial.business.service.AuthService;
import com.alten.mehdilagdimi.product.trial.domain.AuthReq;
import com.alten.mehdilagdimi.product.trial.domain.UserReq;
import com.alten.mehdilagdimi.product.trial.domain.UserResp;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping(value = "/account", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createAccount(@RequestBody @Valid UserReq reqBody){
        UserResp userAccount = authService.createAccount(reqBody);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userAccount.id())
                .toUri();
        return ResponseEntity.created(uri).body(userAccount);
    }

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity authenticate(@RequestBody @Valid AuthReq reqBody) {
        return ResponseEntity.ok(authService.authenticate(reqBody));
    }

}
