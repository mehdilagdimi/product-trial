package com.alten.mehdilagdimi.product.trial.business.mapper;

import com.alten.mehdilagdimi.product.trial.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MappersTestConfig.class)
class WishlistMapperTest {

    private WishList wishList;
    private Product product;
    private UserAccount userAccount;
    @Autowired
    private WishlistMapper wishlistMapper;

    @BeforeEach
    void setup (){
            wishList = new WishList();
            product = new Product();
            product.setId(0L);
            product.setCode("code");
            userAccount = mock(UserAccount.class);
            wishList.setUser(userAccount);
            wishList.setId(1L);
            wishList.setProductList(new ArrayList<>(List.of(product)));
    }

    @Test
    void shouldReturnCorrectCartDto(){
        when(userAccount.getId()).thenReturn(0L);
        when(userAccount.getEmail()).thenReturn("user@email.com");

        WishlistDto wishlistDto = wishlistMapper.toDto(wishList);

        assertNotNull(wishlistDto);
        assertEquals(wishlistDto.id(), wishList.getId());
        assertEquals(wishlistDto.userId(), wishList.getUser().getId());
        assertEquals(wishlistDto.email(), wishList.getUser().getEmail());
        assertTrue(wishlistDto.products().get(0).id().equals( wishList.getProductList().get(0).getId() ));
        assertTrue(wishlistDto.products().get(0).code().equals( wishList.getProductList().get(0).getCode() ));
    }

}