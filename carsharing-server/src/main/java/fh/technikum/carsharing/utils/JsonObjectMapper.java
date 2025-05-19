package fh.technikum.carsharing.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;

public class JsonObjectMapper {

    @Getter
    private static final ObjectMapper instance;

    static {
        instance = new ObjectMapper();
        instance.registerModule(new JavaTimeModule());
    }

    private JsonObjectMapper() {}
}
