package ru.git.lab.bot.services;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.api.mr.Project;
import ru.git.lab.bot.api.mr.Reviewer;
import ru.git.lab.bot.model.entities.ApproveEntity;
import ru.git.lab.bot.model.entities.GitUserEntity;
import ru.git.lab.bot.services.api.MrTextMessageService;
import ru.git.lab.bot.services.api.UserService;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrTextMessageServiceImpl implements MrTextMessageService {

    private static final String likeEmoji = EmojiParser.parseToUnicode(":thumbsup:");
    private static final String DEFAULT_PREFIX = "Тут могло быть ";
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private final UserService userService;

    @Override
    public String createMergeRequestTextMessage(MergeRequestEvent event) {
        Optional<ObjectAttributes> objectAttributes = Optional.ofNullable(event.getObjectAttributes());

        String projectText = getProjectText(event.getProject());
        String title = getTitleText(objectAttributes);
        String description = getDescriptionText(objectAttributes);
        String author = getAuthorText(objectAttributes);
        String reviewer = getReviewerText(event.getReviewers());
        String branch = getBranchText(objectAttributes);
        String createdAt = getCreatedAtText();
        String mrUrl = getMrUrlText(objectAttributes);

        return projectText + title + description + author + reviewer + branch + createdAt + mrUrl;
    }

    @Override
    public String createMergeRequestTextMessageWithApprove(MergeRequestEvent event, List<ApproveEntity> approves) {
        String mergeRequestTextMessage = createMergeRequestTextMessage(event);
        StringBuilder stringBuilder = new StringBuilder(mergeRequestTextMessage);
        approves.forEach(a -> {
            String approveMessage = "\n\n" + likeEmoji + " " + "<b>" + a.getAuthorName() + " approved </b>";
            stringBuilder.append(approveMessage);
        });
        return stringBuilder.toString();
    }

    private String getProjectText(Project project) {
        String projectName = Optional.ofNullable(project)
                .map(Project::getName)
                .orElse(DEFAULT_PREFIX + "название проекта.");

        return "<b>Project:</b> " + projectName + "\n\n";
    }

    private String getTitleText(Optional<ObjectAttributes> objectAttributes) {
        String title = objectAttributes.map(ObjectAttributes::getTitle)
                .orElse(DEFAULT_PREFIX + "название МРа.");

        return "<b>Title:</b> " + title + "\n";
    }

    private String getDescriptionText(Optional<ObjectAttributes> objectAttributes) {
        String description = objectAttributes.map(ObjectAttributes::getDescription)
                .orElse(DEFAULT_PREFIX + "описание МРа");

        return "<b>Description:</b> " + description + "\n\n";
    }

    private String getAuthorText(Optional<ObjectAttributes> objectAttributes) {
        long authorId = objectAttributes.map(ObjectAttributes::getAuthorId)
                .orElseThrow(() -> new RuntimeException("Author in not present"));

        GitUserEntity gitUserEntity = userService.getByAuthorId(authorId);
        String name = gitUserEntity.getName();

        return "<b>Author:</b> " + name + "\n";
    }

    private String getReviewerText(List<Reviewer> reviewers) {
        String reviewerName = reviewers.stream()
                .findFirst()
                .map(Reviewer::getName)
                .orElse("No reviewers available");

        return "<b>Reviewer:</b> " + reviewerName + "\n\n";
    }

    private String getBranchText(Optional<ObjectAttributes> objectAttributes) {
        String sourceBranch = objectAttributes.map(ObjectAttributes::getSourceBranch)
                .orElse("Source branch not present");
        String targetBranch = objectAttributes.map(ObjectAttributes::getTargetBranch)
                .orElse("Target branch not present");

        return "<b>Source:</b> " + sourceBranch + " ==> <b>Target:</b> " + targetBranch + "\n\n";
    }

    private String getCreatedAtText() {
        //TODO Fix date deserializer
//        OffsetDateTime createdAt = objectAttributes
//                .map(ObjectAttributes::getCreatedAt)
//                .orElse(OffsetDateTime.now());
        String createdAt = OffsetDateTime.now()
                .format(DATE_TIME_FORMATTER);

        return "<b>Create date:</b> " + createdAt + "\n\n";
    }

    private String getMrUrlText(Optional<ObjectAttributes> objectAttributes) {
        String mrUrl = objectAttributes.map(ObjectAttributes::getUrl)
                .orElse(StringUtils.EMPTY);

        return "<a href='" + mrUrl + "'><b><u>MR Hyperlink</u></b></a>";
    }
}
