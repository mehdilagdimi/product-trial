package com.alten.mehdilagdimi.product.trial.infra.spring;

import jakarta.servlet.Filter;

public abstract class CustomFilter implements Filter {
    public String urlPattern;
}
