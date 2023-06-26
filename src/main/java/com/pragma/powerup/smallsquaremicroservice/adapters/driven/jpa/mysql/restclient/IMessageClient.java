package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.restclient;

import com.pragma.powerup.smallsquaremicroservice.config.security.feingclient.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "messenger-microservice", configuration = FeignClientConfig.class, url = "${messenger-microservice.url}")
public interface IMessageClient {
    @PostMapping("/sms/")
    void sendSMS(@RequestBody String sms);
}
