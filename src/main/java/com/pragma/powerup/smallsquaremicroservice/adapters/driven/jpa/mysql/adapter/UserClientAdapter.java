package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.adapter;


import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.restclient.IUserClient;
import com.pragma.powerup.smallsquaremicroservice.domain.datasource.IUserClientPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserClientAdapter implements IUserClientPort {
    private final IUserClient userClient;

    @Override
    public boolean getOwner(Long id) {
        return userClient.validUserOwner(id);
    }

    @Override
    public boolean getEmployee(Long id) {
        return userClient.validUserEmployee(id);
    }

    @Override
    public String getClient(Long id) {
        return userClient.getPhoneClient(id);
    }
}
