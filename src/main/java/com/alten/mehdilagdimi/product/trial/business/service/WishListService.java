package com.alten.mehdilagdimi.product.trial.business.service;
import com.alten.mehdilagdimi.product.trial.business.exception.UserNotFoundForAuthException;
import com.alten.mehdilagdimi.product.trial.business.mapper.WishlistMapper;
import com.alten.mehdilagdimi.product.trial.domain.Product;
import com.alten.mehdilagdimi.product.trial.domain.UserAccount;
import com.alten.mehdilagdimi.product.trial.domain.WishList;
import com.alten.mehdilagdimi.product.trial.domain.WishlistDto;
import com.alten.mehdilagdimi.product.trial.infra.repository.ProductRepository;
import com.alten.mehdilagdimi.product.trial.infra.repository.UserAccountRepository;
import com.alten.mehdilagdimi.product.trial.infra.repository.WishListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {

    private final Logger logger = LoggerFactory.getLogger(WishListService.class);
    private final WishlistMapper mapper;
    private final WishListRepository repository;
    private final ProductRepository productRepository;
    private final UserAccountRepository userAccountRepository;

    public WishListService(
            WishListRepository repository,
            ProductRepository productRepository,
            UserAccountRepository userAccountRepository,
            WishlistMapper mapper){
        this.repository = repository;
        this.productRepository = productRepository;
        this.userAccountRepository = userAccountRepository;
        this.mapper = mapper;
    }


    public WishlistDto addProductToWishlist(String email, Long productId) {
        WishList wishlist = repository.findByUserEmail(email);
        UserAccount user =
                userAccountRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new UserNotFoundForAuthException(email));

        Product product =
                productRepository
                        .findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found"));

        if (wishlist == null) {
            wishlist = new WishList();
            wishlist.setUser(user);
            wishlist.setProductList(new ArrayList<>(List.of(product)));
        } else {
            wishlist.getProductList().add(product);
        }

        return mapper.toDto(repository.save(wishlist));
    }

    public WishlistDto removeProductFromWishlist(String email, Long productId) {
        WishList wishlist = repository.findByUserEmail(email);
        if (wishlist != null) {
            wishlist.getProductList()
                    .removeIf(product -> product.getId().equals(productId));
            return mapper.toDto(repository.save(wishlist));
        } else {
            logger.warn("Product not found in wishlist, product ID : {}", productId);
            return null;
        }
    }

    public WishlistDto getWishlist(String email) {
        return mapper.toDto( repository.findByUserEmail(email) );
    }
}