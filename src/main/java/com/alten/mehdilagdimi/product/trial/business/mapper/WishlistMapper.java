package com.alten.mehdilagdimi.product.trial.business.mapper;

import com.alten.mehdilagdimi.product.trial.domain.WishList;
import com.alten.mehdilagdimi.product.trial.domain.WishlistDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface WishlistMapper {

    @Mapping( source = "user.id", target = "userId")
    @Mapping( source = "user.email", target = "email")
    @Mapping( source = "productList", target = "products")
    WishlistDto toDto(WishList p);

}
