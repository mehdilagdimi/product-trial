package com.alten.mehdilagdimi.product.trial.business.mapper;

import com.alten.mehdilagdimi.product.trial.domain.Cart;
import com.alten.mehdilagdimi.product.trial.domain.CartDto;
import com.alten.mehdilagdimi.product.trial.domain.Item;
import com.alten.mehdilagdimi.product.trial.domain.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CartMapperTest {

    private Cart cart;
    private Item item;
    private UserAccount userAccount;
    private CartMapper cartMapper;

    @BeforeEach
    void setup (){
            cartMapper = Mappers.getMapper(CartMapper.class);
            cart = new Cart();
            item = new Item();
            userAccount = mock(UserAccount.class);
            cart.setUser(userAccount);
            cart.setId(1L);
            cart.setItems(new HashSet<>());
            item.setId(2L);
            item.setQuantity(5);
    }

    @Test
    void shouldReturnCorrectCartDto(){
        when(userAccount.getId()).thenReturn(0L);
        when(userAccount.getEmail()).thenReturn("user@email.com");

        CartDto cartDto = cartMapper.toDto(cart);

        assertNotNull(cartDto);
        assertEquals(cartDto.id(), cart.getId());
        assertEquals(cartDto.userId(), cart.getUser().getId());
        assertEquals(cartDto.email(), cart.getUser().getEmail());
        assertTrue(cartDto.items().equals( cart.getItems() ));
    }

}