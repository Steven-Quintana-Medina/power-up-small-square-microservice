package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DishResDto {
    private String name;
    private double price;
    private String description;
    private String urlImage;
}
