package ru.git.lab.bot.services;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.dto.AuthorDto;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.model.entities.ApproveEntity;
import ru.git.lab.bot.model.entities.GitUserEntity;
import ru.git.lab.bot.services.api.MrTextMessageService;
import ru.git.lab.bot.services.api.UserService;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrTextMessageServiceImpl implements MrTextMessageService {

    private static final String likeEmoji = EmojiParser.parseToUnicode(":thumbsup:");

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private final UserService userService;

    @Override
    public String createMergeRequestTextMessage(MergeRequestDto mergeRequest) {
        String projectText = getProjectText(mergeRequest.getProjectName());
        String title = getTitleText(mergeRequest.getTitle());
        String description = getDescriptionText(mergeRequest.getDescription());
        String author = getAuthorText(mergeRequest.getAuthor());
        String reviewer = getReviewerText(mergeRequest.getReviewerName());
        String branch = getBranchText(mergeRequest.getSourceBranch(), mergeRequest.getTargetBranch());
        String createdAt = getCreatedAtText();
        String mrUrl = getMrUrlText(mergeRequest.getMrUrl());

        return projectText + title + description + author + reviewer + branch + createdAt + mrUrl;
    }

    @Override
    public String createMergeRequestTextMessageWithApprove(MergeRequestDto mergeRequest, List<ApproveEntity> approves) {
        String mergeRequestTextMessage = createMergeRequestTextMessage(mergeRequest);
        StringBuilder stringBuilder = new StringBuilder(mergeRequestTextMessage);
        approves.forEach(a -> {
            String approveMessage = "\n\n" + likeEmoji + " " + "<b>" + a.getAuthorName() + " approved </b>";
            stringBuilder.append(approveMessage);
        });
        return stringBuilder.toString();
    }

    private String getProjectText(String name) {
        return "<b>Project:</b> " + name + "\n\n";
    }

    private String getTitleText(String title) {
        return "<b>Title:</b> " + title + "\n";
    }

    private String getDescriptionText(String description) {
        return "<b>Description:</b> " + description + "\n\n";
    }

    private String getAuthorText(AuthorDto author) {
        GitUserEntity gitUserEntity = userService.getByAuthorId(author.getId());
        String name = gitUserEntity.getName();

        return "<b>Author:</b> " + name + "\n";
    }

    private String getReviewerText(String reviewerName) {
        return "<b>Reviewer:</b> " + reviewerName + "\n\n";
    }

    private String getBranchText(String sourceBranch, String targetBranch) {
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

    private String getMrUrlText(String url) {
        return "<a href='" + url + "'><b><u>MR Hyperlink</u></b></a>";
    }
}
