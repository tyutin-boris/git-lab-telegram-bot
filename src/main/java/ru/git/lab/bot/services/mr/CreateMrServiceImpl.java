package ru.git.lab.bot.services.mr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.model.entities.ChatsTgGitUsersEntity;
import ru.git.lab.bot.model.entities.TgMrMessageEntity;
import ru.git.lab.bot.model.repository.ChatsTgGitUsersRepository;
import ru.git.lab.bot.services.api.MrTextMessageService;
import ru.git.lab.bot.services.api.TgMrMessageService;
import ru.git.lab.bot.services.mr.api.CreateMrService;
import ru.git.lab.bot.services.senders.api.MessageSender;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateMrServiceImpl implements CreateMrService {

    private final MessageSender sender;

    private final TgMrMessageService tgMrMessageService;

    private final ChatsTgGitUsersRepository chatsTgGitUsersRepository;

    private final MrTextMessageService mrTextMessageService;

    @Override
    public void sendAndSaveMessage(MergeRequestDto mergeRequest) {
        long mrId = mergeRequest.getMrId();
        long authorId = mergeRequest.getAuthor()
                .getId();

        String mrIdAndAuthorIdLog = "Mr with id " + mrId + " and authorId " + authorId;

        Optional<TgMrMessageEntity> messageEntity = tgMrMessageService.findByMrIdAndAuthorId(mrId, authorId);

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

        List<Long> messageIds = chatIds.stream()
                .map(chatId -> tgMrMessageService.saveMessage(chatId, text, mergeRequest))
                .toList();

        messageIds.stream()
                .map(tgMrMessageService::findTgMrMessageById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::sendMessageToTg)
                .forEach(tgMrMessageService::save);
    }

    private TgMrMessageEntity sendMessageToTg(TgMrMessageEntity entity) {
        if (entity.isDraft()) {
            log.debug("mr with id: {} has draft status", entity.getMrId());
            return entity;
        }

        sender.sendMessage(entity.getText(), entity.getChatId())
                .ifPresent(sent -> {
                    entity.setTgId(sent.getMessageId());
                });

        return entity;
    }
}
