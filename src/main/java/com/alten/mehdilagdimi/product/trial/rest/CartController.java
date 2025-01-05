package com.alten.mehdilagdimi.product.trial.rest;

import com.alten.mehdilagdimi.product.trial.business.service.CartService;
import com.alten.mehdilagdimi.product.trial.business.util.JwtUtil;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity addProductToWishlist(@RequestHeader("Authorization") String token, @PathVariable Long productId) {
        String email = jwtUtil.extractUsername(token);
        return ResponseEntity.ok( cartService.addProductToCart(email, productId) );
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity removeProductFromWishlist(@RequestHeader("Authorization") String token, @PathVariable Long productId) {
        String email = jwtUtil.extractUsername(token);
        cartService.removeProductFromCart(email, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity getCart(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractUsername(token);
        return ResponseEntity.ok( cartService.getCart(email) );
    }
}