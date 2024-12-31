package com.alten.mehdilagdimi.product.trial.infra.spring;

import jakarta.servlet.Filter;

public abstract class AuthFilter implements Filter {
    public String urlPattern;
}
