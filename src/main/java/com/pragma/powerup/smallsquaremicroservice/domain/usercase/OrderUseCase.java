package com.pragma.powerup.smallsquaremicroservice.domain.usercase;

import com.pragma.powerup.smallsquaremicroservice.domain.api.IOrderServicePort;
import com.pragma.powerup.smallsquaremicroservice.domain.datasource.IMessageClientPort;
import com.pragma.powerup.smallsquaremicroservice.domain.datasource.ITraceabilityClientPort;
import com.pragma.powerup.smallsquaremicroservice.domain.datasource.IUserClientPort;
import com.pragma.powerup.smallsquaremicroservice.domain.enums.EnumStatusOrder;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.InvalidAssignEmployeeOrderException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.OrderNotFoundException;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderDish;
import com.pragma.powerup.smallsquaremicroservice.domain.model.OrderPin;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IOrderDishPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IOrderPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IOrderPinPersistencePort;
import com.pragma.powerup.smallsquaremicroservice.domain.spi.IRestaurantEmployeePersistencePort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.pragma.powerup.smallsquaremicroservice.domain.services.OrderService.*;
import static com.pragma.powerup.smallsquaremicroservice.domain.services.RestaurantService.validRange;
import static com.pragma.powerup.smallsquaremicroservice.domain.utils.Constants.CURRENT_DATE;

public class OrderUseCase implements IOrderServicePort {
    private final IOrderPersistencePort orderPersistencePort;
    private final IOrderDishPersistencePort orderDishPersistencePort;
    private final IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort;
    private final IUserClientPort userClientPort;
    private final IMessageClientPort messageClientPort;
    private final IOrderPinPersistencePort orderPinPersistencePort;
    private final ITraceabilityClientPort traceabilityClientPort;

    public OrderUseCase(IOrderPersistencePort orderPersistencePort, IOrderDishPersistencePort orderDishPersistencePort, IRestaurantEmployeePersistencePort restaurantEmployeePersistencePort, IUserClientPort userClientPort, IMessageClientPort messageClientPort, IOrderPinPersistencePort orderPinPersistencePort, ITraceabilityClientPort traceabilityClientPort) {
        this.orderPersistencePort = orderPersistencePort;
        this.orderDishPersistencePort = orderDishPersistencePort;
        this.restaurantEmployeePersistencePort = restaurantEmployeePersistencePort;
        this.userClientPort = userClientPort;
        this.messageClientPort = messageClientPort;
        this.orderPinPersistencePort = orderPinPersistencePort;
        this.traceabilityClientPort = traceabilityClientPort;
    }

    @Override
    public void saveOrder(Order order, Long idClient) {
        order.setIdClient(idClient);
        order.setDate(CURRENT_DATE);
        order.setStatus(EnumStatusOrder.PENDIENTE);
        Order orderEntity = orderPersistencePort.saveOrder(order);

        Map<String, Object> userInfo= userClientPort.getUserClientAndEmployee(order.getIdClient(),order.getIdChef());
        order.getOrderDishes().forEach(orderDish -> orderDish.setIdOrder(orderEntity));

        orderDishPersistencePort.saveOrderDish(order.getOrderDishes());
        traceabilityClientPort.saveTraceability(createTraceabilityMap(order,EnumStatusOrder.PENDIENTE.toString(),userInfo));
    }

    @Override
    public void updateStatusToReady(Long id, Long idEmployee) {
        Order order = orderPersistencePort.getOrderEmployee(id, idEmployee);
        validOrderReady(order, id);
        order.setStatus(EnumStatusOrder.LISTO);

        String phone = userClientPort.getPhoneUserClient(order.getIdClient()).get("phone").toString();
        String pin = codeSms(order, phone);

        messageClientPort.sendPinSms(createMessageMap(order, phone, pin));
        orderPinPersistencePort.saveOrderPin(new OrderPin(order, pin));
        orderPersistencePort.saveOrder(order);
        traceabilityClientPort.updateTraceability(createTraceabilityMap(order,EnumStatusOrder.EN_PREPARACION.toString(),null));
    }

    @Override
    public List<Order> getRestaurantOrder(int pageNumber, int pageSize, String statusOrder, Long idEmployee) {
        validRange(pageNumber, pageSize);
        pageNumber -= 1;
        Long idRestaurant = restaurantEmployeePersistencePort.getRestaurantEmployee(idEmployee).getIdRestaurant().getId();
        List<OrderDish> orderDishes = orderDishPersistencePort.getRestaurantOrderDish(statusOrder, idRestaurant);
        List<Order> orders = orderPersistencePort.getRestaurantOrder(pageNumber, pageSize, statusOrder, idRestaurant);

        for (Order order : orders) {
            for (OrderDish orderDish : orderDishes) {
                if (Objects.equals(order.getId(), orderDish.getIdOrder().getId())) {
                    order.getOrderDishes().add(orderDish);
                }
            }
        }
        return orders;
    }

    @Override
    public List<Order> assignEmployeeOrder(int pageNumber, int pageSize, String statusOrder, List<Long> idOrders, Long idEmployee) {
        List<Order> orders = getRestaurantOrder(pageNumber, pageSize, statusOrder, idEmployee);
        List<Map<String,Object>> traceabilityBatchMaps = new ArrayList<>();
        for (Long idOrder : idOrders) {
            boolean idFound = false;
            for (Order order : orders) {
                if (order.getIdChef() == null && order.getId().equals(idOrder)) {
                    order.setIdChef(idEmployee);
                    order.setStatus(EnumStatusOrder.EN_PREPARACION);
                    traceabilityBatchMaps.add(createTraceabilityMap(order,EnumStatusOrder.PENDIENTE.toString(),null));
                    idFound = true;
                    break;
                } else if (order.getIdChef() != null && order.getId().equals(idOrder)) {
                    throw new InvalidAssignEmployeeOrderException(idOrder);
                }
            }
            if (!idFound) {
                throw new OrderNotFoundException(idOrder);
            }
        }
        orderPersistencePort.saveOrderAll(orders);
        traceabilityClientPort.updateTraceabilityBatch(traceabilityBatchMaps);
        return orders;
    }

    @Override
    public void updateStatusToDelivered(String pin, Long idEmployee) {
        OrderPin orderPin = orderPinPersistencePort.getIdAndeleteOrderPin(pin, idEmployee);
        orderPin.getIdOrder().setStatus(EnumStatusOrder.ENTREGADO);
        orderPersistencePort.saveOrder(orderPin.getIdOrder());
        traceabilityClientPort.updateTraceability(createTraceabilityMap(orderPin.getIdOrder(),EnumStatusOrder.LISTO.toString(),null));
    }

    @Override
    public void updateStatusToCancel(Long idOrder, Long idClient) {
        Order order = orderPersistencePort.getOrderClient(idOrder, idClient);
        orderPersistencePort.saveOrder(validOrderCanceled(order));
        traceabilityClientPort.updateTraceability(createTraceabilityMap(order,EnumStatusOrder.PENDIENTE.toString(),null));
    }
}