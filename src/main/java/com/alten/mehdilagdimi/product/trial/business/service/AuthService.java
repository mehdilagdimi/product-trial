package com.alten.mehdilagdimi.product.trial.business.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.alten.mehdilagdimi.product.trial.business.exception.UserNotFoundForAuthException;
import com.alten.mehdilagdimi.product.trial.business.mapper.UserMapper;
import com.alten.mehdilagdimi.product.trial.business.util.JwtUtil;
import com.alten.mehdilagdimi.product.trial.domain.*;
import com.alten.mehdilagdimi.product.trial.infra.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.alten.mehdilagdimi.product.trial.business.util.HashUtil.toEncryptedPassw;

@Service
public class AuthService {
    private static Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserAccountRepository userAccountRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    public AuthService(
            UserAccountRepository repository,
            UserMapper userMapper,
            JwtUtil jwtUtil){
        this.userAccountRepository = repository;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
    }

    public AuthResp authenticate(AuthReq req) {
        UserAccount user =
                userAccountRepository
                .findByEmail(req.email())
                        .filter(found -> {
                            BCrypt.Result result = BCrypt.verifyer().verify(req.password().toCharArray(), found.getPassword());
                            return result.verified;
                        })
                .orElseThrow(() -> new UserNotFoundForAuthException(req.email()));
        String token = jwtUtil.generateToken(req.email());

        logger.info("Successfully authenticated");
        return new AuthResp(
                user.getUsername(),
                user.getFirstname(),
                user.getEmail(),
                token,
                jwtUtil.expiration);
    }

    public UserResp createAccount(UserReq req){
        return userMapper.toDtoResp( userAccountRepository.save(userMapper.toEntity(req)) );
    }

    public Optional<UserAccount> findByEmail(String email){
        return userAccountRepository.findByEmail(email);
    }
}
