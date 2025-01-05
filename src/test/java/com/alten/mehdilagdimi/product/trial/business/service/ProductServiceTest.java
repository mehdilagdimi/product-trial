package com.alten.mehdilagdimi.product.trial.business.service;

import com.alten.mehdilagdimi.product.trial.business.exception.ImageFailToDeleteException;
import com.alten.mehdilagdimi.product.trial.business.exception.ImageFailToSaveException;
import com.alten.mehdilagdimi.product.trial.business.exception.ProductNotFoundException;
import com.alten.mehdilagdimi.product.trial.business.mapper.ProductMapper;
import com.alten.mehdilagdimi.product.trial.domain.Product;
import com.alten.mehdilagdimi.product.trial.domain.ProductDtoReq;
import com.alten.mehdilagdimi.product.trial.domain.ProductDtoResp;
import com.alten.mehdilagdimi.product.trial.infra.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private ProductService productService;

    private ProductDtoReq productDtoReq;
    private ProductDtoResp productDtoResp;
    private Product product;
    private static final String IMAGE_PATH = "/path/to/images/";

    @BeforeEach
    void setUp() {
        productDtoReq = new ProductDtoReq("code", null, null, null, 50.5f, 0, null, 0, null, (short)0);
        productDtoResp = new ProductDtoResp(1L, "code", null, null, "base64Str", null, 0, 0, null, 0, null, (short)0,  0, 0);
        product = new Product();
        product.setId(1L);
        product.setCode("code");
        product.setPrice(50.5f);
        product.setImage("path/to/img.jpg");
    }

    @Test
    void testGetAll() {
        List<Product> products = List.of(product);

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.toDto(any(Product.class), any())).thenReturn(productDtoResp);

        List<ProductDtoResp> result = productService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("code", result.get(0).code());
        assertEquals("base64Str", result.get(0).image());
    }

    @Test
    void shouldThrowProductNotFoundExceptionWhenProductNotFound() {
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        ProductNotFoundException exception =
                assertThrows(ProductNotFoundException.class, () -> productService.getById(productId));
        assertEquals("Product with ID : " + productId + " is not found", exception.getMessage());
    }

    @Test
    void shouldSaveProductSuccessfuly() throws IOException {
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getName()).thenReturn("img.jpg");
        doNothing().when(multipartFile).transferTo(any(Path.class));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toEntity(any(ProductDtoReq.class), anyString())).thenReturn(product);
        when(productMapper.toDto(any(Product.class), anyString())).thenReturn(productDtoResp);

        ProductDtoResp result = productService.save(productDtoReq, multipartFile);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("code", result.code());
        assertEquals(50.5f, result.price());
        assertEquals("base64Str", result.image());
    }

    @Test
    void shouldThrowImageFailToSaveExceptionWhenImageFailToSave() throws IOException {
        when(multipartFile.isEmpty()).thenReturn(true); // Simulate empty file

        ImageFailToSaveException exception =
                assertThrows(ImageFailToSaveException.class, () -> productService.save(productDtoReq, multipartFile));
        assertEquals("Uploaded image file is empty, ", exception.getMessage());
    }

    @Test
    void testUpdate_Success() throws IOException {
        when(productRepository.existsById(1L)).thenReturn(true);
        when(productRepository.findImageById(1L)).thenReturn(Optional.of("/path/to/img.jpg"));
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getName()).thenReturn("newImage.jpg");

        doNothing().when(multipartFile).transferTo(any(Path.class));

        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toEntity(any(ProductDtoReq.class), anyString(), eq(1L))).thenReturn(product);
        when(productMapper.toDto(any(Product.class), any())).thenReturn(productDtoResp);

        try (MockedStatic<Paths> mockedPaths = mockStatic(Paths.class);
             MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {

            Path mockPath = mock(Path.class);
            mockedPaths.when(() -> Paths.get(anyString())).thenReturn(mockPath);
            mockedFiles.when(() -> Files.exists(any(Path.class))).thenReturn(true);

            ProductDtoResp result = productService.update(1L, productDtoReq, multipartFile);

            assertNotNull(result);
            assertEquals("code", result.code());
            verify(productRepository).save(any(Product.class));
            verify(multipartFile).transferTo(any(Path.class));
        }
    }

    @Test
    void shouldDeleteByIdSuccessfully() {
        when(productRepository.findImageById(1L)).thenReturn(Optional.of("/path/to/img.jpg"));
        doNothing().when(productRepository).deleteById(1L);
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.deleteIfExists(any(Path.class)))
                    .thenReturn(true);

            productService.deleteById(1L);
            verify(productRepository).deleteById(1L);
        }
    }

    @Test
    void shouldThrowImageFailToDeleteExceptioWhenImageFailToDelete() {
        when(productRepository.findImageById(1L)).thenReturn(Optional.of("/path/to/img.jpg"));
        doNothing().when(productRepository).deleteById(1L);

        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.deleteIfExists(any(Path.class)))
                    .thenThrow(new ImageFailToDeleteException("Failed to delete image", new IOException()));

            ImageFailToDeleteException exception =
                    assertThrows(ImageFailToDeleteException.class, () -> productService.deleteById(1L));
            assertEquals("Failed to delete image", exception.getMessage());
        }
    }
}