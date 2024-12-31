package com.alten.mehdilagdimi.product.trial.rest.filter;

import com.alten.mehdilagdimi.product.trial.business.exception.UnauthorizedException;
import com.alten.mehdilagdimi.product.trial.business.service.AuthService;
import com.alten.mehdilagdimi.product.trial.business.util.JwtUtil;
import com.alten.mehdilagdimi.product.trial.infra.spring.CustomFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProductCrudFilter extends CustomFilter {

    private final JwtUtil jwtUtil;
    private final AuthService authService;
    private final String adminEmail;

    public ProductCrudFilter(AuthService authService, JwtUtil jwtUtil, @Value("${admin.email}") String adminEmail){
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.adminEmail = adminEmail;
        this.urlPattern = "/products/*";
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) request;

        String email = (String) httpReq.getAttribute("email");

        if (adminEmail.equals(email)) {
            chain.doFilter(request, response);
            return;
        }

        throw new UnauthorizedException("Only Admin can access the demanded resources");
    }
}
