package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.entity.OrderPinEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderPinRepository extends JpaRepository<OrderPinEntity, Long> {
}
