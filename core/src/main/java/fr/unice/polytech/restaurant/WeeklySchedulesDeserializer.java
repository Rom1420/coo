package fr.unice.polytech.restaurant;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class WeeklySchedulesDeserializer extends JsonDeserializer<Map<DayOfWeek, Map.Entry<LocalTime, LocalTime>>> {

    @Override
    public Map<DayOfWeek, Map.Entry<LocalTime, LocalTime>> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Map<String, Map<String, String>> rawSchedules = p.readValueAs(new TypeReference<Map<String, Map<String, String>>>() {});

        Map<DayOfWeek, Map.Entry<LocalTime, LocalTime>> schedules = new HashMap<>();
        for (Map.Entry<String, Map<String, String>> entry : rawSchedules.entrySet()) {
            DayOfWeek day = DayOfWeek.valueOf(entry.getKey());
            Map<String, String> times = entry.getValue();

            LocalTime openingTime = LocalTime.parse(times.get("opening"));
            LocalTime closingTime = LocalTime.parse(times.get("closing"));

            schedules.put(day, Map.entry(openingTime, closingTime));
        }

        return schedules;
    }
}
