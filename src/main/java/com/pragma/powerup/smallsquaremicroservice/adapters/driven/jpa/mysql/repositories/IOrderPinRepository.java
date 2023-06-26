package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderPinEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IOrderPinRepository extends JpaRepository<OrderPinEntity, Long> {
    @Query("SELECT op FROM OrderPinEntity WHEN op.pin = :pin AND op.idOrder.idChef = :idChef")
    Optional<OrderPinEntity> findByPin(@Param("pin") String pin, @Param("idChef") Long idChef);
}
