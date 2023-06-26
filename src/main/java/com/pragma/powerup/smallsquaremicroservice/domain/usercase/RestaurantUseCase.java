package com.pragma.powerup.smallsquaremicroservice.domain.usercase;

import com.pragma.powerup.smallsquaremicroservice.domain.api.IRestaurantServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.datasource.IUserClientPort;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Restaurant;
import com.pragma.powerup.smallsquaremicroservice.domain.services.RestaurantService;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;

import java.util.List;

import static com.pragma.powerup.smallsquaremicroservice.domain.services.RestaurantService.validRange;

public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserClientPort userClientPort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserClientPort userClientPort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userClientPort = userClientPort;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        boolean user = userClientPort.validUserOwner(restaurant.getIdOwner());
        RestaurantService.ValidUser(user);
        RestaurantService.ValidName(restaurant.getName());
        RestaurantService.ValidNit(restaurant.getNit());
        RestaurantService.ValidPHone(restaurant.getPhone());

        restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant(int pageNumber, int pageSize) {
        validRange(pageNumber, pageSize);
        pageNumber -= 1;
        return restaurantPersistencePort.getAllRestaunrat(pageNumber, pageSize);
    }

}
