package com.pragma.powerup.smallsquaremicroservice.config.converters.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.pragma.powerup.smallsquaremicroservice.config.converters.IJsonConverter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JsonConverterImpl implements IJsonConverter {
    private final static Logger logger = LoggerFactory.getLogger(JsonConverterImpl.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("Error al serializar el objeto JSON: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String toJsonNode(String value, Object object) {
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.putPOJO(value, object);
        try {
            return objectMapper.writeValueAsString(jsonNode);
        } catch (JsonProcessingException e) {
            logger.error("Error al serializar el objeto JSON: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Map<String, Object>  toMap(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);

        Map<String, Object> jsonMap = jsonObject.toMap();
        return null;
    }
}
