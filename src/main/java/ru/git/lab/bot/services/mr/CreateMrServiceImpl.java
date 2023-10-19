package ru.git.lab.bot.services.mr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.DetailedMergeStatus;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.model.entities.ChatsTgGitUsersEntity;
import ru.git.lab.bot.model.entities.TgMrMessageEntity;
import ru.git.lab.bot.model.repository.ChatsTgGitUsersRepository;
import ru.git.lab.bot.services.api.MessageService;
import ru.git.lab.bot.services.api.MrTextMessageService;
import ru.git.lab.bot.services.mr.api.CreateMrService;
import ru.git.lab.bot.services.senders.api.MessageSender;

import java.util.List;
import java.util.Optional;

import static ru.git.lab.bot.api.mr.DetailedMergeStatus.DRAFT_STATUS;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateMrServiceImpl implements CreateMrService {

    private final MessageSender sender;

    private final MessageService messageService;

    private final ChatsTgGitUsersRepository chatsTgGitUsersRepository;

    private final MrTextMessageService mrTextMessageService;

    @Override
    public void sendAndSaveMessage(MergeRequestDto mergeRequest) {
        long mrId = mergeRequest.getMrId();
        long authorId = mergeRequest.getAuthor()
                .getId();
        DetailedMergeStatus detailedMergeStatus = mergeRequest.getDetailedMergeStatus();

        String mrIdAndAuthorIdLog = "Mr with id " + mrId + " and authorId " + authorId;

        if (DRAFT_STATUS.equals(detailedMergeStatus)) {
            log.info("Message not sent because has draft status. " + mrIdAndAuthorIdLog);
            return;
        }

        Optional<TgMrMessageEntity> messageEntity = messageService.findByMrIdAndAuthorId(mrId, authorId);

        if (messageEntity.isPresent()) {
            log.debug("Message already sent. " + mrIdAndAuthorIdLog);
            return;
        }

        List<Long> chatIds = chatsTgGitUsersRepository.findAllByGitId(authorId)
                .stream()
                .map(ChatsTgGitUsersEntity::getChatId)
                .toList();

        if (chatIds.isEmpty()) {
            log.debug("Chat list is empty, message not sant. " + mrIdAndAuthorIdLog);
            return;
        }

        String text = mrTextMessageService.createMergeRequestTextMessage(mergeRequest);

        for (Long id : chatIds) {
            sender.sendMessage(text, id)
                    .ifPresent(message -> messageService.saveMessage(message, mergeRequest));
        }
    }
}
