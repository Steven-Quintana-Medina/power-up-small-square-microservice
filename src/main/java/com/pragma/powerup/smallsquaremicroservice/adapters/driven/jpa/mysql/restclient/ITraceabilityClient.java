package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.restclient;

import com.pragma.powerup.smallsquaremicroservice.config.security.feingclient.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "user-microservice", configuration = FeignClientConfig.class, url = "${traceability-microservice.url}")
public interface ITraceabilityClient {
    @GetMapping("/traceability/")
    void saveTraceability(String traceability);

    @PutMapping("/traceability/")
    void updateTraceability(String traceability);
    @PutMapping("/traceability/batch")
    void updateTraceabilityBatch(String traceability);
}
