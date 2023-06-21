package org.boris.bot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Configuration
public class ObjectMapperConfig {

    @Bean
    @Primary
    public ObjectMapper bootObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule module = new JavaTimeModule();
        module.setDeserializers();
        mapper.registerModule(module);
        return mapper;
    }
}
