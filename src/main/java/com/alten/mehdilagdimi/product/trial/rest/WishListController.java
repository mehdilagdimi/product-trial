package com.alten.mehdilagdimi.product.trial.rest;

import com.alten.mehdilagdimi.product.trial.business.service.WishListService;
import com.alten.mehdilagdimi.product.trial.business.util.JwtUtil;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/wishlist")
public class WishListController {
    private final JwtUtil jwtUtil;
    private final WishListService wishlistService;

    public WishListController(WishListService wishListService, JwtUtil jwtUtil){
        this.wishlistService = wishListService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addProductToWishlist(@RequestHeader("Authorization") String token, @PathVariable @NotNull Long productId) {
        String email = jwtUtil.extractUsername(token);
        return ResponseEntity.ok( wishlistService.addProductToWishlist(email, productId) );
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity removeProductFromWishlist(@RequestHeader("Authorization") String token, @PathVariable @NotNull Long productId) {
        String email = jwtUtil.extractUsername(token);
        wishlistService.removeProductFromWishlist(email, productId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getWishlist(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractUsername(token);
        return ResponseEntity.ok( wishlistService.getWishlist(email) );
    }
}