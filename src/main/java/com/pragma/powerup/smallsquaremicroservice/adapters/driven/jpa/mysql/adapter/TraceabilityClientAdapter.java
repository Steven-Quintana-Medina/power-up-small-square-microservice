package com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.restclient.IMessageClient;
import com.pragma.powerup.smallsquaremicroservice.adapters.driven.jpa.mysql.restclient.ITraceabilityClient;
import com.pragma.powerup.smallsquaremicroservice.config.converters.IJsonConverter;
import com.pragma.powerup.smallsquaremicroservice.domain.datasource.IMessageClientPort;
import com.pragma.powerup.smallsquaremicroservice.domain.datasource.ITraceabilityClientPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class TraceabilityClientAdapter implements ITraceabilityClientPort {
    private final ITraceabilityClient traceabilityClient;
    private final IJsonConverter jsonConverter;
    @Override
    public void saveTraceability(Map<String, Object> traceabilityMap) {
        traceabilityClient.saveTraceability(jsonConverter.toJson(traceabilityMap));
    }

    @Override
    public void updateTraceability(Map<String, Object> traceabilityMap) {
        traceabilityClient.updateTraceability(jsonConverter.toJson(traceabilityMap));
    }

    @Override
    public void updateTraceabilityBatch(List<Map<String, Object>> traceabilityBatchMap) {
        traceabilityClient.updateTraceabilityBatch(jsonConverter.toJson(traceabilityBatchMap));
    }
}
