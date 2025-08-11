package com.weatherfit.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component("json")
public class JsonUtil {
    private final ObjectMapper mapper = new ObjectMapper();

    public String toJson(Object obj) {
        try {
            if (obj == null) {
                return "{}";
            }
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
