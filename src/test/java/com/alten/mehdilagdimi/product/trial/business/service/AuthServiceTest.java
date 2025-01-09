package com.alten.mehdilagdimi.product.trial.business.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.alten.mehdilagdimi.product.trial.business.exception.UserNotFoundForAuthException;
import com.alten.mehdilagdimi.product.trial.business.util.HashUtil;
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
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private BCrypt.Verifyer verifyer;

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
        when(userAccountRepository.findByEmail(authReq.email()))
                .thenReturn(Optional.of(userAccount));

        try(MockedStatic<HashUtil> hashUtilMockedStatic = mockStatic(HashUtil.class)){
            hashUtilMockedStatic.when(
                    () -> HashUtil.isPassVerified(authReq.password(), userAccount.getPassword())
            ).thenReturn(true);
            AuthResp authResp = authService.authenticate(authReq);

            assertNotNull(authResp);
            assertEquals(expectedToken, authResp.token());
            assertEquals(userAccount.getUsername(), authResp.username());
            assertEquals(userAccount.getFirstname(), authResp.firstname());
            assertEquals(userAccount.getEmail(), authResp.email());
            assertEquals(jwtUtil.expiration, authResp.expires_in());
        }

    }

    @Test
    void shouldThrowUserNameNotFoundExceptionWhenNotFoundByEmail() {
        when(userAccountRepository.findByEmail(authReq.email()))
                .thenReturn(Optional.empty());

        UserNotFoundForAuthException exception =
                assertThrows( UserNotFoundForAuthException.class, () -> authService.authenticate(authReq));

        assertEquals("User not found for authentication with provided email/id : " + authReq.email(), exception.getMessage());
    }


}