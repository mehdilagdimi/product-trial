package com.alten.mehdilagdimi.product.trial.business.service;

import com.alten.mehdilagdimi.product.trial.business.exception.NoCartFoundForUserException;
import com.alten.mehdilagdimi.product.trial.business.exception.ProductNotFoundException;
import com.alten.mehdilagdimi.product.trial.business.exception.UserNotFoundForAuthException;
import com.alten.mehdilagdimi.product.trial.business.mapper.CartMapper;
import com.alten.mehdilagdimi.product.trial.domain.*;
import com.alten.mehdilagdimi.product.trial.infra.repository.CartRepository;
import com.alten.mehdilagdimi.product.trial.infra.repository.ProductRepository;
import com.alten.mehdilagdimi.product.trial.infra.repository.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private UserAccountRepository userAccountRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartMapper cartMapper;

    @InjectMocks
    private CartService cartService;

    private Product product;
    private UserAccount userAccount;
    private Item item;
    private Cart cart;
    private CartDto expectedCartDto;

    @BeforeEach
    void setUp() {
        userAccount = new UserAccount();
        userAccount.setUsername("test");
        userAccount.setFirstname("user");
        userAccount.setEmail("user@test.com");
        userAccount.setPassword("password");

        product = new Product();
        product.setId(1L);
        product.setCode("code");
        product.setQuantity(5);
        product.setPrice(50.5f);

        item = new Item();
        item.setProduct(product);

        cart = new Cart();
        cart.setUser(userAccount);
        cart.setItems(new HashSet<>(List.of(item)));

        ProductDtoResp productDtoResp =
                new ProductDtoResp(1L, "code", null, null, null, null, 50.5f, 0, null, 0, InventoryStatus.INSTOCK, (short) 0, 0, 0);

        ItemDto itemDto = new ItemDto();
        itemDto.setQuantity(5);
        itemDto.setProduct(productDtoResp);
        Set<ItemDto> itemDtoSet = new HashSet<>(List.of(itemDto));

        expectedCartDto =
                new CartDto(cart.getId(), cart.getUser().getId(), cart.getUser().getEmail(), itemDtoSet);
    }

    @Test
    void shouldAddProductToCartSuccessfully() {
        String email = "user@test.com";
        Long productId = 1L;

        when(userAccountRepository.findByEmail(email)).thenReturn(Optional.of(userAccount));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.findByUserEmail(email)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        when(cartMapper.toDto(any(Cart.class))).thenReturn(expectedCartDto);

        CartDto cartDto = cartService.addProductToCart(email, productId);

        assertNotNull(cartDto);
        assertEquals(1, cartDto.items().size());
        assertEquals(product.getId(), cartDto.items().iterator().next().getProduct().id());
    }

    @Test
    void shouldAddProductToCartAlreadyInCart() {
        String email = "user@test.com";
        Long productId = 1L;

        when(userAccountRepository.findByEmail(email)).thenReturn(Optional.of(userAccount));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(cartRepository.findByUserEmail(email)).thenReturn(Optional.of(cart));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
        when(cartMapper.toDto(any(Cart.class))).thenReturn(expectedCartDto);

        CartDto res = cartService.addProductToCart(email, productId);

        assertNotNull(res);
        assertEquals(1, res.items().size());
        assertEquals(5, res.items().iterator().next().getQuantity());
    }

    @Test
    void shouldThrowUserNotFoundForAuthExceptionWhenUserNotFound() {
        String email = "nonuser@test.com";
        Long productId = 1L;

        when(userAccountRepository.findByEmail(email)).thenReturn(Optional.empty());

        UserNotFoundForAuthException exception =
                assertThrows(UserNotFoundForAuthException.class, () -> cartService.addProductToCart(email, productId));

        assertEquals("User not found for authentication with provided email/id : " + email, exception.getMessage());
    }

    @Test
    void shouldThrowProductNotFoundExceptionWhenProductNotFound() {
        String email = "user@test.com";
        Long productId = 1L;

        when(userAccountRepository.findByEmail(email)).thenReturn(Optional.of(userAccount));
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        ProductNotFoundException exception =
                assertThrows(ProductNotFoundException.class, () -> cartService.addProductToCart(email, productId)
                );
        assertEquals("Product with ID : " + productId + " is not found", exception.getMessage());
    }


    @Test
    void shouldThrowNoCartFoundForUserExceptionWhenCartNotFound() {
        String email = "user@test.com";
        Long productId = 1L;

        when(cartRepository.findByUserEmail(email)).thenReturn(Optional.empty());

        NoCartFoundForUserException exception =
                assertThrows(NoCartFoundForUserException.class, () -> cartService.removeProductFromCart(email, productId));
        assertEquals("No cart was found for user with email : " + email, exception.getMessage());
    }

    @Test
    void shouldReturnNullWhenNoCartFoundWhileApiGettingCart() {
        String email = "user@test.com";

        when(cartRepository.findByUserEmail(email)).thenReturn(Optional.empty());

        CartDto result = cartService.getCart(email);

        assertNull(result);
    }

    @Test
    void shouldThrowNoCartFoundForUserExceptionWhenNoCartFoundWhileRemovingProductFromCart() {
        String email = "user@test.com";
        Long productId = 1L;

        when(cartRepository.findByUserEmail(email)).thenReturn(Optional.empty());

        NoCartFoundForUserException exception =
                assertThrows(NoCartFoundForUserException.class, () -> cartService.removeProductFromCart(email, productId));

        assertEquals("No cart was found for user with email : " + email, exception.getMessage());
    }

}