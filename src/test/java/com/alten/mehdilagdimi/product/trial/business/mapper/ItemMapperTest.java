package com.alten.mehdilagdimi.product.trial.business.mapper;

import com.alten.mehdilagdimi.product.trial.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MappersTestConfig.class)
class ItemMapperTest {

    private Item item;
    private Product product;
    private UserAccount userAccount;
    @Autowired
    private ItemMapper itemMapper;

    @BeforeEach
    void setup (){
            item = new Item();
            product = new Product();
            product.setId(0L);
            product.setCode("code");
            userAccount = mock(UserAccount.class);
            item.setId(1L);
            item.setProduct(product);
            item.setQuantity(5);
    }

    @Test
    void shouldReturnCorrectCartDto(){
        ItemDto itemDto = itemMapper.toDto(item);

        assertNotNull(itemDto);
        assertEquals(itemDto.getId(), item.getId());
        assertEquals(itemDto.getQuantity(), item.getQuantity());
        assertEquals(itemDto.getProduct().id(), item.getProduct().getId());
        assertEquals(itemDto.getProduct().code(), item.getProduct().getCode());
    }

}