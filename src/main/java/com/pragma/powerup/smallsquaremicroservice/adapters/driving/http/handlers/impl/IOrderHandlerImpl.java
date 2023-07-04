package com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.req.AssignEmployeeOrderDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.req.OrderReqDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.dto.res.OrderResDto;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mappers.req.IOrderReqMapper;
import com.pragma.powerup.smallsquaremicroservice.adapters.driving.http.mappers.res.IOrderResMapper;
import com.pragma.powerup.smallsquaremicroservice.config.security.jwt.JwtService;
import com.pragma.powerup.smallsquaremicroservice.domain.api.IOrderServicePort;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IOrderHandlerImpl implements IOrderHandler {
    private final IOrderServicePort orderServicePort;
    private final IOrderReqMapper orderReqMapper;
    private final IOrderResMapper orderResMapper;
    private final JwtService jwtService;
    private final HttpServletRequest request;

    @Override
    public void saveOrder(OrderReqDto orderReqDto) {
        orderServicePort.saveOrder(orderReqMapper.toOrder(orderReqDto), jwtService.getIdToken(request.getHeader("Authorization")));
    }

    @Override
    public void updateStatusToReady(Long id) {
        orderServicePort.updateStatusToReady(id, jwtService.getIdToken(request.getHeader("Authorization")));
    }

    @Override
    public void updateStatusToCancel(Long idOrder) {
        orderServicePort.updateStatusToCancel(idOrder, jwtService.getIdToken(request.getHeader("Authorization")));
    }

    @Override
    public void updateStatusToDelivered(String pin) {
        orderServicePort.updateStatusToDelivered(pin, jwtService.getIdToken(request.getHeader("Authorization")));
    }

    @Override
    public List<OrderResDto> getRestaurantOrders(int pageNumber, int pageSize, String statusOrder) {
        return orderResMapper.toOrderRes(orderServicePort.getRestaurantOrder(pageNumber, pageSize, statusOrder, jwtService.getIdToken(request.getHeader("Authorization"))));
    }

    @Override
    public List<OrderResDto> assignEmployeeOrder(int pageNumber, int pageSize, String statusOrder, AssignEmployeeOrderDto assignEmployeeOrderDto) {
        return orderResMapper.toOrderRes(orderServicePort.assignEmployeeOrder(pageNumber, pageSize, statusOrder, assignEmployeeOrderDto.getIdOrders(), jwtService.getIdToken(request.getHeader("Authorization"))));
    }
}
