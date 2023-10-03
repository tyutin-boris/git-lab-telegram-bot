package ru.git.lab.bot.dto;

import lombok.Data;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.DetailedMergeStatus;

@Data
public class MergeRequestDto {

    long mrId;

    UserDto user;

    AuthorDto author;

    Action action;

    DetailedMergeStatus detailedMergeStatus;

    String projectName;

    String title;

    String description;

    String reviewerName;

    String sourceBranch;

    String targetBranch;

    String createdDate;

    String mrUrl;
}
