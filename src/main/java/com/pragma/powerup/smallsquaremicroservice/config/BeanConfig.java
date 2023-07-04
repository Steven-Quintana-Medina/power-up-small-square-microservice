package com.pragma.powerup.smallsquaremicroservice.config;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.adapter.*;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers.*;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.*;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.restclient.IMessageClient;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.restclient.ITraceabilityClient;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.restclient.IUserClient;
import com.pragma.powerup.smallsquaremicroservice.config.converters.IJsonConverter;
import com.pragma.powerup.smallsquaremicroservice.domain.api.*;
import com.pragma.powerup.smallsquaremicroservice.domain.datasource.IMessageClientPort;
import com.pragma.powerup.smallsquaremicroservice.domain.datasource.ITraceabilityClientPort;
import com.pragma.powerup.smallsquaremicroservice.domain.datasource.IUserClientPort;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.*;
import com.pragma.powerup.smallsquaremicroservice.domain.usercase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IUserClient userClient;
    private final IMessageClient messageClient;
    private final ITraceabilityClient traceabilityClient;
    private final IJsonConverter jsonConverter;
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;
    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IRestaurantEmployeeRepository restaurantEmployeeRepository;
    private final IRestaurantEmployeeEntityMapper restaurantEmployeeEntityMapper;
    private final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;
    private final IOrderPinRepository orderPinRepository;
    private final IOrderPinEntityMapper orderPinEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryMysqlAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantMysqlAdapter(restaurantRepository, restaurantEntityMapper);
    }

    @Bean
    public IUserClientPort userClientPort() {
        return new UserClientAdapter(userClient,jsonConverter);
    }

    @Bean
    public IMessageClientPort messageClientPort() {
        return new MessageClientAdapter(messageClient,jsonConverter);
    }

    @Bean
    public ITraceabilityClientPort traceabilityClientPort(){
        return new TraceabilityClientAdapter(traceabilityClient,jsonConverter);
    }
    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), userClientPort());
    }

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishMysqlAdapter(dishRepository, dishEntityMapper, categoryRepository, restaurantRepository);
    }

    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort());
    }

    @Bean
    public IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort() {
        return new RestaurantEmployeeMysqlAdapter(restaurantRepository, restaurantEmployeeRepository, restaurantEmployeeEntityMapper);
    }

    @Bean
    public IRestaurantEmployeeServicePort restaurantEmployeeServicePort() {
        return new RestaurantEmployeeUseCase(restaurantEmployeePersistencePort(), userClientPort());
    }

    @Bean
    public IOrderDishPersistencePort orderDishPersistencePort() {
        return new OrderDishMysqlAdapter(orderDishRepository, orderDishEntityMapper);
    }

    @Bean
    public IOrderPinPersistencePort orderPinPersistencePort() {
        return new OrderPinMysqlAdapter(orderPinRepository, orderPinEntityMapper);
    }

    @Bean
    public IOrderPersistencePort orderPersistencePort() {
        return new OrderMysqlAdapter(orderRepository, orderEntityMapper);
    }

    @Bean
    public IOrderServicePort orderServicePort() {
        return new OrderUseCase(orderPersistencePort(), orderDishPersistencePort(), restaurantEmployeePersistencePort(), userClientPort(), messageClientPort(), orderPinPersistencePort(), traceabilityClientPort());
    }
}
