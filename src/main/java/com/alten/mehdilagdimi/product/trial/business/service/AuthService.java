package com.alten.mehdilagdimi.product.trial.business.service;

import com.alten.mehdilagdimi.product.trial.business.util.JwtUtil;
import com.alten.mehdilagdimi.product.trial.domain.*;
import com.alten.mehdilagdimi.product.trial.infra.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private static Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserAccountRepository userAccountRepository;
    private final JwtUtil jwtUtil;

    public AuthService(
            UserAccountRepository repository,
            JwtUtil jwtUtil){
        this.userAccountRepository = repository;
        this.jwtUtil = jwtUtil;
    }

    public AuthResp authenticate(AuthReq req) {
        UserAccount user =
                userAccountRepository
                .findByEmailAndPassword(req.email(), req.password())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + req.email()));
        String token = jwtUtil.generateToken(req.email());

        logger.info("Successfully authenticated");
        return new AuthResp(
                user.username(),
                user.firstname(),
                user.email(),
                token,
                jwtUtil.expiration);
    }

    public UserAccount createAccount(UserAccount req){
        return userAccountRepository.save(req);
    }

    public Optional<UserAccount> findByEmail(String email){
        return userAccountRepository.findByEmail(email);
    }
}
