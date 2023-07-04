package com.pragma.powerup.smallsquaremicroservice.config.converters;

import java.util.Map;

public interface IJsonConverter {
    String toJson(Object object);

    String toJsonNode(String value, Object object);

    Map<String, Object> toMap(String jsonString);
}
