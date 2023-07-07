package ru.git.lab.bot.api.mr;

import lombok.Data;

@Data
public class Repository {
    String name;
    String url;
    String description;
    String homepage;
}
