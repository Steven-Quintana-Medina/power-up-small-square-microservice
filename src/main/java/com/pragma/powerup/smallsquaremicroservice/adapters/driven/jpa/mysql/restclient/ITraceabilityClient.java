package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.restclient;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.pragma.powerup.smallsquaremicroservice.config.security.feingclient.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "traceability-microservice", configuration = FeignClientConfig.class, url = "${traceability-microservice.url}")
public interface ITraceabilityClient {
    @PostMapping(value = "/traceability/", produces = MediaType.APPLICATION_JSON_VALUE)
    void saveTraceability(@RequestBody String traceability);

    @PutMapping("/traceability/")
    void updateTraceability(String traceability);
    @PutMapping("/traceability/batch")
    void updateTraceabilityBatch(String traceability);
}
