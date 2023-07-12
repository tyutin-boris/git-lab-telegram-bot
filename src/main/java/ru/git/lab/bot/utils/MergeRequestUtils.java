package ru.git.lab.bot.utils;

import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.api.mr.Project;
import ru.git.lab.bot.api.mr.Reviewer;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MergeRequestUtils {

    private static final String DEFAULT_PREFIX = "Тут могло быть ";
    private final static DateTimeFormatter DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");


    public static String createMergeRequestMessage(MergeRequestEvent request) {
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
        String createdAt = OffsetDateTime.now().format(DATE_TIME_FORMATTER);
        String mrUrl = objectAttributes
                .map(ObjectAttributes::getUrl)
                .orElse("");
        String sourceBranch = request.getObjectAttributes()
                .getSourceBranch();
        String targetBranch = request.getObjectAttributes()
                .getTargetBranch();

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

        return "<b>Project:</b> " + projectName + "\n\n" +
                "<b>Title:</b> " + title + "\n" +
                "<b>Description:</b> " + description + "\n\n" +
                "<b>Author:</b> " + name + "\n" +
                "<b>Reviewer:</b> " + reviewerName + "\n\n" +
                "<b>Source:</b> " + sourceBranch + " ==> " +
                "<b>Target:</b> " + targetBranch + "\n\n" +
                "<b>Create date:</b> " + createdAt + "\n\n" +
                "<a href='" + mrUrl + "'><b><u>MR Hyperlink</u></b></a>";
    }
}
