package ru.git.lab.bot.dto;

import lombok.Data;
import ru.git.lab.bot.api.mr.Action;

@Data
public class MergeRequestDto {

    long mrId;

    UserDto user;

    AuthorDto author;

    Action action;

    String projectName;

    String title;

    String description;

    String reviewerName;

    String sourceBranch;

    String targetBranch;

    String mrUrl;

    boolean isDraft;
}
