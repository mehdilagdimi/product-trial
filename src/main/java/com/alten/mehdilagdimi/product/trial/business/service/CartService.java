package com.alten.mehdilagdimi.product.trial.business.service;

import com.alten.mehdilagdimi.product.trial.business.exception.NoCartFoundForUserException;
import com.alten.mehdilagdimi.product.trial.business.exception.ProductNotFoundException;
import com.alten.mehdilagdimi.product.trial.business.exception.UserNotFoundForAuthException;
import com.alten.mehdilagdimi.product.trial.business.mapper.CartMapper;
import com.alten.mehdilagdimi.product.trial.domain.*;
import com.alten.mehdilagdimi.product.trial.infra.repository.CartRepository;
import com.alten.mehdilagdimi.product.trial.infra.repository.ProductRepository;
import com.alten.mehdilagdimi.product.trial.infra.repository.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private static Logger logger = LoggerFactory.getLogger(CartService.class);

    private final CartMapper cartMapper;
    private final CartRepository repository;
    private final ProductRepository productRepository;
    private final UserAccountRepository userAccountRepository;

    public CartService(
            CartRepository repository,
            ProductRepository productRepository,
            UserAccountRepository userAccountRepository,
            CartMapper cartMapper){
        this.repository = repository;
        this.productRepository = productRepository;
        this.userAccountRepository = userAccountRepository;
        this.cartMapper = cartMapper;
    }

    public CartDto addProductToCart(String email, Long productId) {
        Optional<Cart> existingCart = repository.findByUserEmail(email);
        UserAccount user =
                userAccountRepository
                        .findByEmail(email)
                        .orElseThrow(() -> new UserNotFoundForAuthException(email));
        Product product =
                productRepository
                        .findById(productId)
                        .orElseThrow(() -> new ProductNotFoundException(productId));
        Item item = new Item().setProduct(product);
        Cart cart;
        if (existingCart.isPresent()) {
            cart = existingCart.get();
            Optional<Item> itemInCart =
                    cart.items()
                            .stream()
                            .filter(i -> item.product().equals(product))
                            .findFirst();

            itemInCart.ifPresentOrElse(
                    i ->  i.setQuantity(i.quantity() + 1),
                    () ->  cart.items().add(item)
            );

        } else {
            cart = new Cart()
                            .setUser(user)
                            .setItems(new HashSet<>(List.of(item)));
        }

        return cartMapper.toDto( repository.save(cart) );
    }

    public CartDto removeProductFromCart(String email, Long productId) {
        Optional<Cart> cartOptional = repository.findByUserEmail(email);
        if (cartOptional.isPresent()) {
            Cart cart = cartOptional.get();
            cart.items().removeIf(item -> item.product().id().equals(productId));
            return cartMapper.toDto( repository.save( cart ) );
        } else {
            throw new NoCartFoundForUserException(email);
        }
    }

    public CartDto getCart(String email) {
        return cartMapper.toDto(repository.findByUserEmail(email).orElse(null));
    }


}