package com.alten.mehdilagdimi.product.trial.rest;

import com.alten.mehdilagdimi.product.trial.business.service.AuthService;
import com.alten.mehdilagdimi.product.trial.domain.AuthReq;
import com.alten.mehdilagdimi.product.trial.domain.UserAccount;
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
    public ResponseEntity createAccount(@RequestBody UserAccount reqBody){
        UserAccount userAccount = authService.createAccount(reqBody);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userAccount.getId())
                .toUri();
        return ResponseEntity.created(uri).body(authService.createAccount(reqBody));
    }

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity authenticate(@RequestBody AuthReq reqBody) throws ParseException {
        return ResponseEntity.ok(authService.authenticate(reqBody));
    }

}
