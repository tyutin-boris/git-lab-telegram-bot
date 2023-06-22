package org.boris.bot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.boris.bot.config.ObjectMapperConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.IOException;

@SpringJUnitConfig(classes = ObjectMapperConfig.class)
public class ObjectMapperTest {

    @Autowired
    @Qualifier("bootObjectMapper")
    private ObjectMapper bootObjectMapper;

    @Test
    public void objectMapper() throws IOException {
        Event event = bootObjectMapper.readValue("{\"date\":\"2013-12-03T17:15:43Z\"}", Event.class);
        System.out.println(event);
    }

    @Test
    public void objectMapperTwo() throws IOException {
        Event event = bootObjectMapper.readValue("{\"date\":\"2017-09-15 16:50:55 UTC\"}", Event.class);
        System.out.println(event);
    }

    @Test
    public void objectMapperThree() throws IOException {
        Event event = bootObjectMapper.readValue("{\"date\":\"2020-07-21T12:12:23.000+0200\"}", Event.class);
        System.out.println(event);
    }

    @Test
    public void objectMapperFour() throws IOException {
        Event event = bootObjectMapper.readValue("{\"date\":\"2021-09-30T15:30:00+01:00\"}", Event.class);
        System.out.println(event);
    }

}
