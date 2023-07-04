package com.pragma.powerup.smallsquaremicroservice.domain.usecase;

import com.pragma.powerup.smallsquaremicroservice.domain.datasource.IUserClientPort;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IRestaurantPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.domain.usercase.RestaurantUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.pragma.powerup.smallsquaremicroservice.domain.utils.RestaurantModelValues.INSTANCE_RESTAURANT;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RestaurantUseCaseTest {
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private IUserClientPort userClientPort;
    @InjectMocks
    private RestaurantUseCase restaurantUseCaseMock;


    @Test
    void testSaveOwner() {
        when(userClientPort.validUserOwner(INSTANCE_RESTAURANT.getIdOwner())).thenReturn(true);

        restaurantUseCaseMock.saveRestaurant(INSTANCE_RESTAURANT);

        verify(userClientPort).validUserOwner(INSTANCE_RESTAURANT.getIdOwner());
        verify(restaurantPersistencePort).saveRestaurant(INSTANCE_RESTAURANT);
    }

    @Test
    void testGetAllRestaurat() {
        int pageNumber = 1;
        int pageSize = 1;
        restaurantUseCaseMock.getAllRestaurant(pageNumber, pageSize);
        verify(restaurantPersistencePort).getAllRestaunrat(pageNumber -= 1, pageSize);
    }


}
