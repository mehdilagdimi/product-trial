package com.alten.mehdilagdimi.product.trial.business.service;

import com.alten.mehdilagdimi.product.trial.business.exception.ProductNotFoundException;
import com.alten.mehdilagdimi.product.trial.business.exception.UserNotFoundForAuthException;
import com.alten.mehdilagdimi.product.trial.domain.Cart;
import com.alten.mehdilagdimi.product.trial.domain.Product;
import com.alten.mehdilagdimi.product.trial.domain.UserAccount;
import com.alten.mehdilagdimi.product.trial.infra.repository.CartRepository;
import com.alten.mehdilagdimi.product.trial.infra.repository.ProductRepository;
import com.alten.mehdilagdimi.product.trial.infra.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class CartService {

    private final CartRepository repository;
    private final ProductRepository productRepository;
    private final UserAccountRepository userAccountRepository;

    public CartService(CartRepository repository, ProductRepository productRepository, UserAccountRepository userAccountRepository){
        this.repository = repository;
        this.productRepository = productRepository;
        this.userAccountRepository = userAccountRepository;
    }

    public Cart addProductToCart(String email, Long productId) {
        Cart cart = repository.findByUserEmailAndIsActive(email, true);
        UserAccount user =
                userAccountRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new UserNotFoundForAuthException(email));
        if (cart == null) {
            cart = new Cart(null, user, new HashSet<>(), true);
        }

        Product product =
                productRepository
                        .findById(productId)
                        .orElseThrow(() -> new ProductNotFoundException(productId));

        cart.productList().add(product);
        return repository.save(cart);
    }

    public Cart removeProductFromCart(String email, Long productId) {
        Cart cart = repository.findByUserEmailAndIsActive(email, true);
        if (cart != null) {
            cart.productList().removeIf(product -> product.id().equals(productId));
            return repository.save(cart);
        }
        return null;
    }

    public Cart getCart(String email) {
        return repository.findByUserEmailAndIsActive(email, true);
    }
}