package com.pragma.powerup.smallsquaremicroservice.domain.services;

import com.pragma.powerup.smallsquaremicroservice.domain.enums.EnumStatusOrder;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.NonCancellableOrderException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.OrderAlreadyNotifiedException;
import com.pragma.powerup.smallsquaremicroservice.domain.exceptions.OrderNotFoundException;
import com.pragma.powerup.smallsquaremicroservice.domain.model.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class OrderService {

    public static void validOrderReady(Order order, Long id) {
        if (order == null) {
            throw new OrderNotFoundException(id);
        } else if (order.getStatus().equals(EnumStatusOrder.LISTO)) {
            throw new OrderAlreadyNotifiedException();
        }
    }


    public static Order validOrderCanceled(Long idOrder,Order order) {
        if(order == null){
            throw new OrderNotFoundException(idOrder);
        }
        else if (order.getStatus().equals(EnumStatusOrder.PENDIENTE)) {
            order.setStatus(EnumStatusOrder.CANCELADO);
            return order;
        }
        throw new NonCancellableOrderException();
    }

    public static Map<String, String> createMessageMap(Order order, String phone, String pin) {
        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("phone", phone);
        messageMap.put("restaurantPhone", order.getIdRestaurant().getPhone());
        messageMap.put("pin", pin);

        return messageMap;
    }

    public static Map<String, Object> createTraceabilityMap(Order order, String previousState, Map<String, Object> infoClient,Map<String, Object> infoEmployee) {
        Map<String, Object> traceabilityMap = new HashMap<>();

        traceabilityMap.put("idOrder", order.getId());
        traceabilityMap.put("idClient", order.getIdClient());
        traceabilityMap.put("mailClient", infoClient.get("mail"));
        traceabilityMap.put("currentState", order.getStatus().toString());
        traceabilityMap.put("previousState", previousState);
        traceabilityMap.put("idEmployee", order.getIdChef() != null ? order.getIdChef(): null);
        traceabilityMap.put("mailEmployee", infoEmployee != null ? infoClient.get("mail") : null);
        return traceabilityMap;
    }



    public static String codeSms(Order order, String phone) {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(order.getId());
        hash = 79 * hash + Objects.hashCode(order.getIdRestaurant().getPhone());
        hash = 79 * hash + Objects.hashCode(phone);
        return String.valueOf(Math.abs(hash));
    }
}
