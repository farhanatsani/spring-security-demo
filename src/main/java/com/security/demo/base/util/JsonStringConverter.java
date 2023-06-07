package com.security.demo.base.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.security.demo.base.ErrorMessage;
import com.security.demo.base.ResponseMessage;

import java.time.LocalDateTime;

public class JsonStringConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String ERROR_MESSAGE = "Fail to convert";
    public static <T> T toJsonObject(String content, T valueType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.readValue(content, (JavaType) valueType);
        } catch (Exception e) {
            System.err.printf("JsonStringConverter.toJsonObject {}", ERROR_MESSAGE);
        }
        return valueType;
    }
    public static String toJsonString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            System.err.printf("JsonStringConverter.toJsonString {}", ERROR_MESSAGE);
            e.printStackTrace();
        }
        return "";
    }

}
