package org.boris.bot.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class UpdatedAt {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Zagreb")
    Date previous;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Zagreb")
    Date current;
}
