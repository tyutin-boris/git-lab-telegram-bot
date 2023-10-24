package ru.git.lab.bot.api.pipeline;

import lombok.Data;
import ru.git.lab.bot.api.mr.Author;

import java.util.List;

@Data
public class Commit {

    private String id;

    private String message;

    private String title;

    private String timestamp;

    private String url;

    private Author author;
}
