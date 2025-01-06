package com.alten.mehdilagdimi.product.trial.business.service;

import com.alten.mehdilagdimi.product.trial.business.exception.UserNotFoundForAuthException;
import com.alten.mehdilagdimi.product.trial.business.util.JwtUtil;
import com.alten.mehdilagdimi.product.trial.domain.AuthReq;
import com.alten.mehdilagdimi.product.trial.domain.AuthResp;
import com.alten.mehdilagdimi.product.trial.domain.UserAccount;
import com.alten.mehdilagdimi.product.trial.infra.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserAccountRepository userAccountRepository;

    @InjectMocks
    private AuthService authService;

    private AuthReq authReq;
    private UserAccount userAccount;

    @BeforeEach
    void setUp() {
        authReq = new AuthReq("user@test.com", "password");
        userAccount = new UserAccount();
        userAccount.setUsername("test");
        userAccount.setFirstname("user");
        userAccount.setEmail("user@test.com");
        userAccount.setPassword("password");
    }


    @Test
    void shouldAuthenticateSuccessfully() {
        String expectedToken = "token";

        when(jwtUtil.generateToken(authReq.email())).thenReturn(expectedToken);
        when(userAccountRepository.findByEmailAndPassword(authReq.email(), authReq.password()))
                .thenReturn(Optional.of(userAccount));

        AuthResp authResp = authService.authenticate(authReq);

        assertNotNull(authResp);
        assertEquals(expectedToken, authResp.token());
        assertEquals(userAccount.getUsername(), authResp.username());
        assertEquals(userAccount.getFirstname(), authResp.firstname());
        assertEquals(userAccount.getEmail(), authResp.email());
        assertEquals(jwtUtil.expiration, authResp.expires_in());
    }

    @Test
    void shouldThrowUserNameNotFoundExceptionWhenNotFoundByEmail() {
        when(userAccountRepository.findByEmailAndPassword(authReq.email(), authReq.password()))
                .thenReturn(Optional.empty());

        UserNotFoundForAuthException exception =
                assertThrows( UserNotFoundForAuthException.class, () -> authService.authenticate(authReq));

        assertEquals("User not found for authentication with provided email/id : " + authReq.email(), exception.getMessage());
    }


}