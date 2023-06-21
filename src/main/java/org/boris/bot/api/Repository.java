package org.boris.bot.api;

import lombok.Data;

@Data
public class Repository {
    String name;
    String url;
    String description;
    String homepage;
}