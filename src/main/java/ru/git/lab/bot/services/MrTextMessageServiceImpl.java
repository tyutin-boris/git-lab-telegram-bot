package ru.git.lab.bot.services;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.dto.AuthorDto;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.model.entities.GitUserEntity;
import ru.git.lab.bot.model.entities.TgMrMessageEntity;
import ru.git.lab.bot.services.api.ApproveService;
import ru.git.lab.bot.services.api.GitUserService;
import ru.git.lab.bot.services.api.MrTextMessageService;
import ru.git.lab.bot.services.api.TgMrMessageService;
import ru.git.lab.bot.services.pipelines.api.PipelineStatusTextService;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class MrTextMessageServiceImpl implements MrTextMessageService {

    private static final String likeEmoji = EmojiParser.parseToUnicode(":thumbsup:");

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private final GitUserService gitUserService;

    private final ApproveService approveService;

    private final PipelineStatusTextService pipelineStatusTextService;

    private final TgMrMessageService tgMrMessageService;

    @Override
    public String createMergeRequestText(MergeRequestDto mergeRequest) {
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
    public String createMrMessage(Long mrId) {
        String mrText = getMrText(mrId);
        String approvals = getApprovals(mrId);
        String pipelines = pipelineStatusTextService.createText(mrId);

        return mrText + approvals + pipelines;
    }

    @Override
    public String createMrMessage(Long mrId, String mrText) {
        String approvals = getApprovals(mrId);
        String pipelines = pipelineStatusTextService.createText(mrId);

        return mrText + approvals + pipelines;
    }

    private String getMrText(Long mrId) {
        return tgMrMessageService.findByMrId(mrId)
                .map(TgMrMessageEntity::getText)
                .orElseThrow(() -> new RuntimeException("Mr with id " + mrId + " not found"));
    }

    private String getApprovals(Long mrId) {
        StringBuilder stringBuilder = new StringBuilder();
        approveService.findAllByMrIdAndIsDeleteFalse(mrId)
                .forEach(a -> {
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
        GitUserEntity gitUserEntity = gitUserService.getByAuthorId(author.getId());
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
