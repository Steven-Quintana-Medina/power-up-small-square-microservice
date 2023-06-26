package com.pragma.powerup.smallsquaremicroservice.domain.datasource;

import java.util.Map;

public interface IMessageClientPort {
    void sendPinSms(Map<String, String> sms);
}
