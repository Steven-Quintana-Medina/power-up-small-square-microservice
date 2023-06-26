package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.adapter;


import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.restclient.IUserClient;
import com.pragma.powerup.smallsquaremicroservice.config.converters.IJsonConverter;
import com.pragma.powerup.smallsquaremicroservice.domain.datasource.IUserClientPort;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class UserClientAdapter implements IUserClientPort {
    private final IUserClient userClient;
    private final IJsonConverter jsonConverter;

    @Override
    public boolean validUserOwner(Long id) {
        return !userClient.validUserOwner(id).isEmpty();
    }

    @Override
    public boolean validUserEmployee(Long id) {
        return !userClient.validUserEmployee(id).isEmpty();
    }

    @Override
    public Map<String, Object> getPhoneUserClient(Long id) {
        return jsonConverter.toMap(userClient.getPhoneClient(id));
    }

    @Override
    public Map<String, Object> getUserClientAndEmployee(Long idClient, Long idEmployee) {
        return null;
    }
}
