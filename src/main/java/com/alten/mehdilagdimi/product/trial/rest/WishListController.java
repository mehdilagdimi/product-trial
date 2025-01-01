package com.alten.mehdilagdimi.product.trial.rest;

import com.alten.mehdilagdimi.product.trial.business.service.WishListService;
import com.alten.mehdilagdimi.product.trial.business.util.JwtUtil;
import com.alten.mehdilagdimi.product.trial.domain.WishList;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/wishlist")
public class WishListController {
    private final JwtUtil jwtUtil;
    private final WishListService wishlistService;

    public WishListController(WishListService wishListService, JwtUtil jwtUtil){
        this.wishlistService = wishListService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/{productId}")
    public WishList addProductToWishlist(@RequestHeader("Authorization") String token, @PathVariable Long productId) {
        String email = jwtUtil.extractUsername(token);
        return wishlistService.addProductToWishlist(email, productId);
    }

    @DeleteMapping("/{productId}")
    public WishList removeProductFromWishlist(@RequestHeader("Authorization") String token, @PathVariable Long productId) {
        String email = jwtUtil.extractUsername(token);
        return wishlistService.removeProductFromWishList(email, productId);
    }

    @GetMapping
    public WishList getWishlist(@RequestHeader("Authorization") String token) {
        String email = jwtUtil.extractUsername(token);
        return wishlistService.getWishlist(email);
    }
}