package ru.git.lab.bot.api.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class OffsetDateTimeDeserializer extends JsonDeserializer<OffsetDateTime> {

    @Override
    public OffsetDateTime deserialize(JsonParser p, DeserializationContext context) throws IOException {

        final ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = new Timestamp(p.getValueAsLong()).toLocalDateTime();
        ZoneOffset zoneOffSet = zone.getRules()
                .getOffset(localDateTime);
        return localDateTime.atOffset(zoneOffSet);
    }
}
