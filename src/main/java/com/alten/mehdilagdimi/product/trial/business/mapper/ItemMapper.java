package com.alten.mehdilagdimi.product.trial.business.mapper;

import com.alten.mehdilagdimi.product.trial.domain.Item;
import com.alten.mehdilagdimi.product.trial.domain.ItemDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface ItemMapper {
    ItemDto toDto(Item item);
}
