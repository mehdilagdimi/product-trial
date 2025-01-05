package com.alten.mehdilagdimi.product.trial.business.service;

import com.alten.mehdilagdimi.product.trial.business.exception.ImageFailToDeleteException;
import com.alten.mehdilagdimi.product.trial.business.exception.ImageFailToSaveException;
import com.alten.mehdilagdimi.product.trial.business.exception.ProductNotFoundException;
import com.alten.mehdilagdimi.product.trial.business.mapper.ProductMapper;
import com.alten.mehdilagdimi.product.trial.domain.Product;
import com.alten.mehdilagdimi.product.trial.domain.ProductDtoReq;
import com.alten.mehdilagdimi.product.trial.domain.ProductDtoResp;
import com.alten.mehdilagdimi.product.trial.infra.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private static Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository repository;
    private final ProductMapper productMapper;
    private final String prdImgsDir;

    public ProductService(
            ProductRepository repository,
            ProductMapper productMapper,
            @Value("${products.imgsDir") String prdImgsDir) {
        this.repository = repository;
        this.productMapper = productMapper;
        this.prdImgsDir = prdImgsDir;
    }

    public List<ProductDtoResp> getAll(){
        List<Product> products = repository.findAll();
        return products
                .stream()
                .map( p -> productMapper.toDto(p, prdImgsDir))
                .toList();
    }

    public ProductDtoResp getById(Long id) {
        Product product =
                    repository
                    .findById(id)
                    .orElseThrow(() -> new ProductNotFoundException(id));
        return
                productMapper.toDto(product, prdImgsDir);
    }

    public ProductDtoResp save(ProductDtoReq product, MultipartFile image) {
        String imgPath = archiveImage(image);

        Product p = repository
                    .save(productMapper.toEntity(product, imgPath));
        return productMapper.toDto(p, prdImgsDir);
    }


    public ProductDtoResp update(Long id, ProductDtoReq productReq, MultipartFile image) {
        if(null != id && doesExist(id)) {
            logger.info("Proceeding with update...", id);
            Optional<String> currentImagePath =
                    repository
                            .findImageById(id);

            String newImagePath;
            if(!currentImagePath.isPresent()) {
                logger.warn("No image path was found saved for this product ID {), proceeding with saving new image", id);
            }
//            else {
//                deleteImage(currentImagePath.get(), image);
//                newImagePath = archiveImage(image);
//            }

            newImagePath = archiveImage(image);

            Product p = repository
                        .save(productMapper.toEntity(productReq, newImagePath, id));
            return productMapper.toDto(p, prdImgsDir);
        } else {
            logger.warn("Update not possible...", id);
            throw new UnsupportedOperationException("Creation of entity through through PATCH is not allowed");
        }
    }

    @Transactional
    public void deleteById(Long id) {
        repository
                    .deleteById(id);

        Optional<String> path = repository.findImageById(id);

        if(path.isPresent()) {
            deleteImage(path.get());
        } else {
            logger.warn("No image path was found saved for this product ID {), proceeding with saving new image", id);
        }
        logger.info("Product has been deleted, ID : {}", id);
    }

    private boolean doesExist(Long id){
        if(repository.existsById(id)){
            logger.info("Product with ID : {} exists:");
            return true;
        } else {
            logger.info("Product with ID : {} does not exist:");
            return false;
        }
    }

    private void deleteImage(String currentImgPath){
        try {
            Files.deleteIfExists( Paths.get(prdImgsDir + currentImgPath) );
        } catch (IOException e){
            throw new ImageFailToDeleteException("Failed to delete current product image : " + currentImgPath, e);
        }
    }

    private String archiveImage(MultipartFile multipartFile)  {
        if(multipartFile.isEmpty()){
            logger.warn("File is empty, configuration does not allow saving product without image");
            throw new ImageFailToSaveException("Uploaded image file is empty, ");
        }

        String imgPath = multipartFile.getName();
        try {
            Path path = Paths.get(prdImgsDir);
            if(!Files.exists(path)){
                logger.info("Images base directory does not exist, proceeding with creating it... ");
                Files.createDirectory(path);
            }

            String fullPath =  prdImgsDir + "/" + imgPath;
            path = Paths.get(fullPath);

            logger.info("Saving product image... ");
            multipartFile.transferTo(path);
            return imgPath;
        } catch (IOException e){
            throw new ImageFailToSaveException(
                    "Fail to save image into provided destination, File name : " + imgPath +  " Destination: " + prdImgsDir,
                    e);
        }
    }

}
