package main_service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.time.LocalTime;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JaxsonUtils {
    private JaxsonUtils() {
    }

    public static String toJson(Object object) throws JsonProcessingException {
        ObjectMapper mapper = getObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        ObjectMapper mapper = getObjectMapper();
        try {
            return mapper.readValue(json, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        ObjectMapper mapper = getObjectMapper();
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Ajouter un module pour gérer LocalTime
        SimpleModule module = new SimpleModule();
        module.addKeyDeserializer(LocalTime.class, new LocalTimeKeyDeserializer());
        module.addDeserializer(LocalTime.class, new JsonDeserializer<LocalTime>() {
            @Override
            public LocalTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                return LocalTime.parse(p.getText());
            }
        });

        mapper.registerModule(module);

        // Gestion des dates/temps
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }

    static class LocalTimeKeyDeserializer extends KeyDeserializer {
        @Override
        public LocalTime deserializeKey(String key, DeserializationContext ctxt) throws IOException {
            return LocalTime.parse(key);
        }
    }
}
