package org.boris.bot.services;

import lombok.RequiredArgsConstructor;
import org.boris.bot.api.MergeRequest;
import org.boris.bot.api.ObjectAttributes;
import org.boris.bot.api.Project;
import org.boris.bot.api.Reviewer;
import org.boris.bot.model.ChatEntity;
import org.boris.bot.repository.ChatRepository;
import org.boris.bot.senders.MergeRequestSender;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MergeRequestServiceImpl implements MergeRequestService {

    private static final String DEFAULT_PREFIX = "Тут могло быть ";

    private final MergeRequestSender sender;
    private final ChatRepository chatRepository;

    @Override
    public void sendMergeRequestOpen(MergeRequest request) {
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
                "\n\n" + "Create date: " + createdAt + "\n\n" + "Autor: " + name + "\n\n" + "Username: " + username +
                "\n\n" + "Reviewer: " + reviewerName + "\n\n" + "Source: " + sourceBranch + " ==> " +
                "Target: " + targetBranch;

        List<Long> chatsId = chatRepository.findAll()
                .stream()
                .map(ChatEntity::getId).collect(Collectors.toList());

        for (Long id : chatsId) {
            Message message = sender.sendMessage(text, id);
            System.out.println();
        }
    }

    @Override
    public void sendMergeRequestClose(MergeRequest request) {

    }
}
