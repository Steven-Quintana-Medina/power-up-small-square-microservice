package com.pragma.powerup.smallsquaremicroservice.domain.usercase;

import com.pragma.powerup.smallsquaremicroservice.domain.api.IRestaurantEmployeeServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.datasource.IUserClientPort;
import com.pragma.powerup.smallsquaremicroservice.domain.model.RestaurantEmployee;
import com.pragma.powerup.smallsquaremicroservice.domain.services.RestaurantService;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IRestaurantEmployeePersistencePort;

public class RestaurantEmployeeUseCase implements IRestaurantEmployeeServicePort {
    private final IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;
    private final IUserClientPort userClientPort;

    public RestaurantEmployeeUseCase(IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort, IUserClientPort userClientPort) {
        this.restaurantEmployeePersistencePort = restaurantEmployeePersistencePort;
        this.userClientPort = userClientPort;
    }

    @Override
    public void assignRestaurantEmployee(RestaurantEmployee restaurantEmployee, Long idOwner) {
        boolean user = userClientPort.validUserEmployee(restaurantEmployee.getIdEmployee());
        RestaurantService.ValidUser(user);
        restaurantEmployeePersistencePort.assignRestaurantEmployee(restaurantEmployee, idOwner);
    }

    @Override
    public RestaurantEmployee getRestaurantEmployee(Long idEmployee) {
        return restaurantEmployeePersistencePort.getRestaurantEmployee(idEmployee);
    }


}
