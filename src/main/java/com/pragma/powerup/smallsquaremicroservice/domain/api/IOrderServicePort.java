package com.pragma.powerup.smallsquaremicroservice.domain.api;

import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;

import java.util.List;

public interface IOrderServicePort {
    void saveOrder(Order order, Long idClient);

    void updateStatusToReady(Long id, Long idEmployee);

    void updateStatusToDelivered(String pin, Long idEmployee);

    void updateStatusToCancel(Long idOrder, Long idClient);

    List<Order> getRestaurantOrder(int pageNumber, int pageSize, String statusOrder, Long idEmployee);

    List<Order> assignEmployeeOrder(int pageNumber, int pageSize, String statusOrder, List<Long> idOrders, Long idEmployee);
}
