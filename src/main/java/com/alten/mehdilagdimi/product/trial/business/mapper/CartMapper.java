package com.alten.mehdilagdimi.product.trial.business.mapper;

import com.alten.mehdilagdimi.product.trial.domain.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = ItemMapper.class)
public interface CartMapper {

    @Mapping( source = "user.id", target = "userId")
    @Mapping( source = "user.email", target = "email")
    CartDto toDto(Cart p);
}
