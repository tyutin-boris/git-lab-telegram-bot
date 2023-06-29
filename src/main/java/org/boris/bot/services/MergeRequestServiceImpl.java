package org.boris.bot.services;

import org.boris.bot.api.MergeRequest;
import org.boris.bot.api.ObjectAttributes;
import org.boris.bot.api.Project;
import org.boris.bot.api.Reviewer;
import org.boris.bot.senders.MergeRequestSender;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MergeRequestServiceImpl implements MergeRequestService {

    private static final String DEFAULT_PREFIX = "Тут могло быть ";
    private final MergeRequestSender sender;
    private final String chatId = "586815794";

    public MergeRequestServiceImpl(MergeRequestSender sender) {
        this.sender = sender;
    }

    @Override
    public void sendMergeRequest(MergeRequest request) {
        String projectName = Optional.ofNullable(request.getProject())
                .map(Project::getName)
                .orElse(DEFAULT_PREFIX + "название проекта.");
        String title = Optional.ofNullable(request.getObjectAttributes())
                .map(ObjectAttributes::getTitle)
                .orElse(DEFAULT_PREFIX + "название МРа.");
        String description = Optional.ofNullable(request.getObjectAttributes())
                .map(ObjectAttributes::getDescription)
                .orElse(DEFAULT_PREFIX + "описание МРа");
        OffsetDateTime createdAt = Optional.ofNullable(request.getObjectAttributes())
                .map(ObjectAttributes::getCreatedAt)
                .orElse(OffsetDateTime.now());
        String sourceBranch = request.getObjectAttributes()
                .getSourceBranch();
        String targetBranch = request.getObjectAttributes()
                .getTargetBranch();

        String username = request.getUser()
                .getUsername();
        String name = request.getUser()
                .getName();

        List<Reviewer> reviewers = Optional.ofNullable(request.getReviewers()).orElse(Collections.emptyList());
        Reviewer reviewer = reviewers.stream()
                .findFirst()
                .orElse(null);
        String reviewerName = Optional.ofNullable(reviewer)
                .map(Reviewer::getName)
                .orElse("");

        String text = "Project: " + projectName + "\n\n" + "Title: " + title + "\n\n" + "Description: " + description +
                "\n\n" + "Create date: " + createdAt + "\n\n" + "Autor: " + name + "\n\n" + "Username: " + username +
                "\n\n" + "Reviewer: " + reviewerName + "\n\n" + "Source branch: " + sourceBranch + " ==> " +
                "Target branch: " + targetBranch;

        sender.sendMessage(text, chatId);
    }
}