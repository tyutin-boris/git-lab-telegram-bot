package ru.git.lab.bot.utils;

import ru.git.lab.bot.api.mr.MergeRequest;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.api.mr.Project;
import ru.git.lab.bot.api.mr.Reviewer;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MergeRequestUtils {

    private static final String DEFAULT_PREFIX = "Тут могло быть ";


    public static String createMergeRequestMessage(MergeRequest request) {
        String projectName = Optional.ofNullable(request.getProject())
                .map(Project::getName)
                .orElse(DEFAULT_PREFIX + "название проекта.");
        Optional<ObjectAttributes> objectAttributes = Optional.ofNullable(request.getObjectAttributes());
        String title = objectAttributes
                .map(ObjectAttributes::getTitle)
                .orElse(DEFAULT_PREFIX + "название МРа.");
        String description = objectAttributes
                .map(ObjectAttributes::getDescription)
                .orElse(DEFAULT_PREFIX + "описание МРа");
        //TODO Fix date deserializer
//        OffsetDateTime createdAt = objectAttributes
//                .map(ObjectAttributes::getCreatedAt)
//                .orElse(OffsetDateTime.now());
        OffsetDateTime createdAt = OffsetDateTime.now();
        String mrUrl = objectAttributes
                .map(ObjectAttributes::getUrl)
                .orElse("");
        String sourceBranch = request.getObjectAttributes()
                .getSourceBranch();
        String targetBranch = request.getObjectAttributes()
                .getTargetBranch();

        String username = request.getUser()
                .getUsername();
        String name = request.getUser()
                .getName();

        List<Reviewer> reviewers = Optional.ofNullable(request.getReviewers())
                .orElse(Collections.emptyList());
        Reviewer reviewer = reviewers.stream()
                .findFirst()
                .orElse(null);
        String reviewerName = Optional.ofNullable(reviewer)
                .map(Reviewer::getName)
                .orElse("");

        String text = "Project: " + projectName + "\n\n" + "MP: " + mrUrl + "\n\n" + "Title: " + title + "\n\n" + "Description: " + description +
                "\n\n" + "Create date: " + createdAt + "\n\n" + "Author: " + name + "\n\n" +
                "\n\n" + "Reviewer: " + reviewerName + "\n\n" + "Source: " + sourceBranch + " ==> " +
                "Target: " + targetBranch;
        return text;
    }
}
