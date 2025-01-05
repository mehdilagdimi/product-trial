package com.alten.mehdilagdimi.product.trial.business.mapper;

import com.alten.mehdilagdimi.product.trial.business.exception.ImageFailToLoadException;
import com.alten.mehdilagdimi.product.trial.domain.Product;
import com.alten.mehdilagdimi.product.trial.domain.ProductDtoReq;
import com.alten.mehdilagdimi.product.trial.domain.ProductDtoResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.logging.Logger;

@Mapper(componentModel = "spring")
public interface ProductMapper {

//    Logger logger = Logger.getLogger(ProductMapper.class);

    ProductDtoResp toDto(Product p);

    @Mapping(target = "image", expression = "java(toBase64(p.getName(), basePath))")
    ProductDtoResp toDto(Product p, String basePath);

    Product toEntity(ProductDtoReq p, String image);

    Product toEntity(ProductDtoReq p, String image, Long id);

    default String toBase64 (String imgName, String basePath){
        String path = basePath + "/" + imgName;
        try {
            byte[] data = Files.readAllBytes(Paths.get(path));
//            logger.info("Successfully loaded image, proceeding with encoding to string base64...");
            return Base64.getEncoder().encodeToString(data);
        } catch (IOException e){
            throw new ImageFailToLoadException("Failed to load image : " + path, e);
        }
    }
}
