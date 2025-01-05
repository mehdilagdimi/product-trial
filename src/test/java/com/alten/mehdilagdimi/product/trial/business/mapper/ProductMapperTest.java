package com.alten.mehdilagdimi.product.trial.business.mapper;

import com.alten.mehdilagdimi.product.trial.business.exception.ImageFailToLoadException;
import com.alten.mehdilagdimi.product.trial.domain.InventoryStatus;
import com.alten.mehdilagdimi.product.trial.domain.Product;
import com.alten.mehdilagdimi.product.trial.domain.ProductDtoReq;
import com.alten.mehdilagdimi.product.trial.domain.ProductDtoResp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductMapperTest {

    private String basePath;
    private Product product;
    private ProductDtoReq productDtoReq;

    private ProductMapper mapper;

    @BeforeEach
    void setUp(){
        basePath = "testPath";

        product =
                new Product(
                        1L,
                        "code",
                        "ProductName",
                        "descri",
                        "pathTo/image.jpg",
                        "category",
                        200.3f,
                        5,
                        "intRef",
                        1,
                        InventoryStatus.LOWSTOCK,
                        (short) 5,
                        11111L,
                        11111L);
        productDtoReq =
                new ProductDtoReq(
                        "code",
                        "ProductName",
                        "descri",
                        "category",
                        200.3f,
                        5,
                        "intRef",
                        1,
                        InventoryStatus.LOWSTOCK,
                        (short) 5);
    }

    @Test
    void shouldMapProductEntityToProductDtoResp(){
        mapper = spy(Mappers.getMapper(ProductMapper.class));
        String base64Str = "base64String";

        doReturn(base64Str).when(mapper).toBase64(anyString(), anyString());

        ProductDtoResp prdDto = mapper.toDto(product, basePath);

        assertNotNull(prdDto);
        assertEquals(base64Str, prdDto.image());
        assertThat(product)
                .usingRecursiveComparison()
                .ignoringFields("image")
                .isEqualTo(prdDto);
    }

    @Test
    void shouldReturnBase64EncodedString() throws IOException {
        byte[] data = {0, 0};
        String encodedata = Base64.getEncoder().encodeToString(data);
        mapper = Mappers.getMapper(ProductMapper.class);

        try(MockedStatic<Files> mockedFiles = mockStatic(Files.class)){
            mockedFiles.when( ()-> Files.readAllBytes(any())).thenReturn(data);
            String result = mapper.toBase64("name", basePath);

            assertEquals(encodedata, result);
        }
    }

    @Test
    void shouldThrowImageFailToLoadException() throws IOException {
        mapper = Mappers.getMapper(ProductMapper.class);

        try(MockedStatic<Files> mockedFiles = mockStatic(Files.class)){
            mockedFiles.when( ()-> Files.readAllBytes(any())).thenThrow(new IOException());
//        when(Files.readAllBytes(any())).thenThrow(new IOException());

            assertThrows(
                    ImageFailToLoadException.class,
                    () -> mapper.toBase64("name", basePath));
        }
    }

}