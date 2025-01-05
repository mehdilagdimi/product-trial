package com.alten.mehdilagdimi.product.trial.business.mapper;

import com.alten.mehdilagdimi.product.trial.domain.WishList;
import com.alten.mehdilagdimi.product.trial.domain.WishlistDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    Logger logger = LoggerFactory.getLogger(WishlistMapper.class);

    @Mapping( source = "user.id", target = "userId")
    @Mapping( source = "user.email", target = "email")
    WishlistDto toDto(WishList p);

}
