package org.boris.bot.api;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class LastEditedAt {
    OffsetDateTime previous;
    OffsetDateTime current;
}
