package com.alten.mehdilagdimi.product.trial.rest;

import com.alten.mehdilagdimi.product.trial.business.service.ProductService;
import com.alten.mehdilagdimi.product.trial.domain.ProductDtoReq;
import com.alten.mehdilagdimi.product.trial.domain.ProductDtoResp;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController (ProductService productService){
        this.productService = productService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllProducts(){
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping(value = "/:id", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getProductById(@PathVariable @NotNull Long id){
        return ResponseEntity.ok(productService.getById(id));
    }

    @PatchMapping(value = "/:id", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateProductById(
            @PathVariable @NotNull Long id,
            @RequestPart ProductDtoReq reqBody,
            @RequestPart MultipartFile image){
        return ResponseEntity.ok(productService.update(id, reqBody, image));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addProduct(
            @RequestPart ProductDtoReq reqBody,
            @RequestPart("image") MultipartFile image){
        ProductDtoResp dto = productService.save(reqBody, image);
        URI uri = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(dto.id())
                    .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @DeleteMapping(value = "/:id")
    public ResponseEntity deleteProductById(@RequestParam @NotNull Long id){
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
