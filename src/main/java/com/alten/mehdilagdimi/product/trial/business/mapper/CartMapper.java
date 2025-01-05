package com.alten.mehdilagdimi.product.trial.business.mapper;

import com.alten.mehdilagdimi.product.trial.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Mapper(componentModel = "spring")
public interface CartMapper {

    Logger logger = LoggerFactory.getLogger(CartMapper.class);

    @Mapping( source = "user.id", target = "userId")
    @Mapping( source = "user.email", target = "email")
    CartDto toDto(Cart p);

}
