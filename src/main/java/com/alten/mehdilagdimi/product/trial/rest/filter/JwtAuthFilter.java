package com.alten.mehdilagdimi.product.trial.rest.filter;

import com.alten.mehdilagdimi.product.trial.business.exception.JwtTokenExpiredException;
import com.alten.mehdilagdimi.product.trial.business.exception.UserNotFoundForAuthException;
import com.alten.mehdilagdimi.product.trial.business.service.AuthService;
import com.alten.mehdilagdimi.product.trial.business.util.JwtUtil;
import com.alten.mehdilagdimi.product.trial.domain.UserAccount;
import com.alten.mehdilagdimi.product.trial.infra.spring.AuthFilter;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthFilter extends AuthFilter {

    private final JwtUtil jwtUtil;
    private final AuthService authService;

    public JwtAuthFilter(AuthService authService, JwtUtil jwtUtil){
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.urlPattern = "/*";
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;

        String path = httpReq.getRequestURI();
        if (path.contains("/account") || path.contains("/token")) {
            chain.doFilter(request, response);
            return;
        }

        String authHeader = httpReq.getHeader("Authorization");
        String token;
        String email;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtUtil.extractUsername(token);

            if(jwtUtil.isTokenExpired(token)){
                throw new JwtTokenExpiredException(email);
            }

            Optional<UserAccount> user = authService.findByEmail(email);
            if(!user.isPresent()){
                throw new UserNotFoundForAuthException(email);
            }

            httpReq.setAttribute("email", email);
        }

        chain.doFilter(request, response);
    }
}
