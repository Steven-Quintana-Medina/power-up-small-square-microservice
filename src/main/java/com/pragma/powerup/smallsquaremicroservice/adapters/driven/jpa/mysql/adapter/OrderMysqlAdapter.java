package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.mappers.IOrderEntityMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IOrderPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class OrderMysqlAdapter implements IOrderPersistencePort {
    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;

    @Override
    public Order saveOrder(Order order) {
        return orderEntityMapper.toOrder(orderRepository.saveAndFlush(orderEntityMapper.toEntity(order)));
    }

    @Override
    public List<Order> getRestaurantOrder(int pageNumber, int pageSize, String statusOrder, Long idRestaurant) {
        PageRequest pageable = PageRequest.of(pageNumber, pageSize);
        return orderEntityMapper.toOrder(orderRepository.findByIdRestaurant(pageable, statusOrder, idRestaurant));
    }

    @Override
    public void saveOrderAll(List<Order> orders) {
        orderRepository.saveAll(orderEntityMapper.toEntity(orders));
    }

    @Override
    public Order getOrderEmployee(Long idOrder, Long idChef) {
        Optional<OrderEntity> order = orderRepository.findByIdAndIdChef(idOrder, idChef);
        return orderEntityMapper.toOrder(order.orElse(null));
    }

    @Override
    public Order getOrderClient(Long idOrder, Long idClient) {
        Optional<OrderEntity> order = orderRepository.findByIdAndIdClient(idOrder, idClient);
        return orderEntityMapper.toOrder(order.orElse(null));
    }
}
