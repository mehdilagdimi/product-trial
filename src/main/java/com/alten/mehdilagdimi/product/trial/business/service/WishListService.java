package com.alten.mehdilagdimi.product.trial.business.service;
import com.alten.mehdilagdimi.product.trial.business.exception.UserNotFoundForAuthException;
import com.alten.mehdilagdimi.product.trial.domain.Product;
import com.alten.mehdilagdimi.product.trial.domain.UserAccount;
import com.alten.mehdilagdimi.product.trial.domain.WishList;
import com.alten.mehdilagdimi.product.trial.infra.repository.ProductRepository;
import com.alten.mehdilagdimi.product.trial.infra.repository.UserAccountRepository;
import com.alten.mehdilagdimi.product.trial.infra.repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class WishListService {

    private final WishListRepository repository;
    private final ProductRepository productRepository;
    private final UserAccountRepository userAccountRepository;

    public WishListService(WishListRepository repository, ProductRepository productRepository, UserAccountRepository userAccountRepository){
        this.repository = repository;
        this.productRepository = productRepository;
        this.userAccountRepository = userAccountRepository;
    }


    public WishList addProductToWishlist(String email, Long productId) {
        WishList wishlist = repository.findByUserEmail(email);
        UserAccount user =
                userAccountRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new UserNotFoundForAuthException(email));

        if (wishlist == null) {
            wishlist = new WishList(null, user, new HashSet<>());
        }

        Product product =
                productRepository
                        .findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found"));

        wishlist.productList().add(product);
        return repository.save(wishlist);
    }

    public WishList removeProductFromWishList(String email, Long productId) {
        WishList wishlist = repository.findByUserEmail(email);
        if (wishlist != null) {
            wishlist.productList()
                    .removeIf(product -> product.id().equals(productId));
            return repository.save(wishlist);
        }
        return null;
    }

    public WishList getWishlist(String email) {
        return repository.findByUserEmail(email);
    }
}