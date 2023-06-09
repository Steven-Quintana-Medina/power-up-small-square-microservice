package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IDishEntityMapper {
    @Mapping(target = "idRestaurant.id", source = "idRestaurant.id")
    @Mapping(target = "idCategory.id", source = "idCategory.id")
    DishEntity toEntity(Dish dish);

    @Mapping(target = "idRestaurant.id", source = "idRestaurant.id")
    @Mapping(target = "idCategory.id", source = "idCategory.id")
    List<Dish> toDish(Page<DishEntity> dishEntity);
}
