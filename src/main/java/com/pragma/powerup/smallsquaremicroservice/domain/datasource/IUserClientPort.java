package com.pragma.powerup.smallsquaremicroservice.domain.datasource;

import java.util.Map;

public interface IUserClientPort {
    boolean validUserOwner(Long id);

    boolean validUserEmployee(Long id);

    Map<String, Object> getPhoneUserClient(Long id);

    Map<String, Object> getUserClientAndEmployee(Long idClient, Long idEmployee);
}
