package org.boris.bot.controller;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Event {
    private OffsetDateTime date;

//    @JsonSetter("date")
//    public void setOdt(String odtString) {
//        final String pattern = "yyyy-MM-dd'T'HH:mm:ss";
//        DateTimeFormatter dtfB = DateTimeFormatter.ofPattern(pattern);
//        this.date = OffsetDateTime.parse(odtString, dtfB);
//    }
}
