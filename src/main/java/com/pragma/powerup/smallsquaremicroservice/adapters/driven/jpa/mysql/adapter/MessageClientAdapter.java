package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.restclient.IMessageClient;
import com.pragma.powerup.smallsquaremicroservice.config.converters.IJsonConverter;
import com.pragma.powerup.smallsquaremicroservice.domain.datasource.IMessageClientPort;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class MessageClientAdapter implements IMessageClientPort {
    private final IMessageClient messageClient;
    private final IJsonConverter jsonConverter;

    @Override
    public void sendPinSms(Map<String, String> messageJson) {
        messageClient.sendPinSMS(jsonConverter.toJson(messageJson));
    }
}
