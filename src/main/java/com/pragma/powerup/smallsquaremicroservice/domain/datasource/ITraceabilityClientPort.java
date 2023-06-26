package com.pragma.powerup.smallsquaremicroservice.domain.datasource;

import java.util.List;
import java.util.Map;

public interface ITraceabilityClientPort {
    void saveTraceability(Map<String,Object> traceabilityMap);
    void updateTraceability(Map<String,Object> traceabilityMap);
    void updateTraceabilityBatch(List<Map<String,Object>> traceabilityBatchMap);
}
