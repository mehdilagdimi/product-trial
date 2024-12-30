package com.alten.mehdilagdimi.product.trial.rest;

import com.alten.mehdilagdimi.product.trial.business.service.ProductService;
import com.alten.mehdilagdimi.product.trial.domain.ProductDtoReq;
import com.alten.mehdilagdimi.product.trial.domain.ProductDtoResp;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/products")
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
    public ResponseEntity getProductById(@RequestParam @NotNull Long id){
        return ResponseEntity.ok(productService.getById(id));
    }

    @PatchMapping(value = "/:id", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateProductById(@RequestParam @NotNull Long id, @RequestBody ProductDtoReq reqBody){
        return ResponseEntity.ok(productService.update(id, reqBody));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addProduct(@RequestBody ProductDtoReq reqBody){
        ProductDtoResp dto = productService.save(reqBody);
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
