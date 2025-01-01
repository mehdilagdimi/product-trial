package com.alten.mehdilagdimi.product.trial.rest;

import com.alten.mehdilagdimi.product.trial.business.service.CartService;
import com.alten.mehdilagdimi.product.trial.business.util.JwtUtil;
import com.alten.mehdilagdimi.product.trial.domain.Cart;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
public class CartController {
    private final JwtUtil jwtUtil;
    private final CartService cartService;

    public CartController(CartService cartService, JwtUtil jwtUtil){
        this.cartService = cartService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/{productId}")
    public Cart addProductToWishlist(@RequestHeader("Authorization") String token, @PathVariable Long productId) {
        String email = jwtUtil.extractUsername(token);
        return cartService.addProductToCart(email, productId);
    }

    @DeleteMapping("/{productId}")
    public Cart removeProductFromWishlist(@RequestHeader("Authorization") String token, @PathVariable Long productId) {
        String email = jwtUtil.extractUsername(token);
        return cartService.removeProductFromCart(email, productId);
    }

    @GetMapping
    public Cart getWishlist(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractUsername(token);
        return cartService.getCart(email);
    }
}