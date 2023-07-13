package ru.git.lab.bot.utils;

import com.vdurmont.emoji.EmojiParser;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.api.mr.Project;
import ru.git.lab.bot.api.mr.Reviewer;
import ru.git.lab.bot.model.entities.ApproveEntity;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MergeRequestUtils {

    private static final String likeEmoji = EmojiParser.parseToUnicode(":thumbsup:");
    private static final String DEFAULT_PREFIX = "Тут могло быть ";
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static String createMergeRequestMessage(MergeRequestEvent event) {
        String projectName = Optional.ofNullable(event.getProject())
                .map(Project::getName)
                .orElse(DEFAULT_PREFIX + "название проекта.");
        Optional<ObjectAttributes> objectAttributes = Optional.ofNullable(event.getObjectAttributes());
        String title = objectAttributes.map(ObjectAttributes::getTitle)
                .orElse(DEFAULT_PREFIX + "название МРа.");
        String description = objectAttributes.map(ObjectAttributes::getDescription)
                .orElse(DEFAULT_PREFIX + "описание МРа");
        //TODO Fix date deserializer
//        OffsetDateTime createdAt = objectAttributes
//                .map(ObjectAttributes::getCreatedAt)
//                .orElse(OffsetDateTime.now());
        String createdAt = OffsetDateTime.now()
                .format(DATE_TIME_FORMATTER);
        String mrUrl = objectAttributes.map(ObjectAttributes::getUrl)
                .orElse("");
        String sourceBranch = event.getObjectAttributes()
                .getSourceBranch();
        String targetBranch = event.getObjectAttributes()
                .getTargetBranch();

        //TODO fix to user from db by authorId from objectAttributes
        String name = event.getUser()
                .getName();

        List<Reviewer> reviewers = Optional.ofNullable(event.getReviewers())
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

    public static String createMergeRequestMessageWithApprove(MergeRequestEvent event, List<ApproveEntity> approves) {
        StringBuilder stringBuilder = new StringBuilder(createMergeRequestMessage(event));

        approves.forEach(a -> {
            String approveMessage = "\n\n" + likeEmoji + " " + "<b>" + a.getAuthorName() + " approved </b>";
            stringBuilder.append(approveMessage);
        });
        return stringBuilder.toString();
    }
}
