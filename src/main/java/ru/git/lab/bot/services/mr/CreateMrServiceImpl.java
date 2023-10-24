package ru.git.lab.bot.services.mr;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.model.entities.ChatsTgGitUsersEntity;
import ru.git.lab.bot.model.entities.MessageChatsEntity;
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
    @Transactional
    public void  sendAndSaveMessage(MergeRequestDto mergeRequest) {
        long mrId = mergeRequest.getMrId();
        long authorId = mergeRequest.getAuthor()
                .getId();

        Optional<TgMrMessageEntity> message = tgMrMessageService.findByMrId(mrId);

        List<Long> chatIds = chatsTgGitUsersRepository.findAllByGitId(authorId)
                .stream()
                .map(ChatsTgGitUsersEntity::getChatId)
                .toList();

        if (chatIds.isEmpty()) {
            log.debug("Chat list is empty, message not sent. mrId: {}, authorId: {}", mrId, authorId);
            return;
        }

        String text = mrTextMessageService.createMergeRequestTextMessage(mergeRequest);

        Long messageId;

        if (message.isEmpty()) {
            messageId = tgMrMessageService.save(chatIds, text, mergeRequest);
        } else {
            TgMrMessageEntity tgMrMessage = message.get();
            messageId = tgMrMessage.getId();
            tgMrMessage.setDelete(false);
            tgMrMessage.setText(text);
            tgMrMessage.setDraft(mergeRequest.isDraft());
        }

        TgMrMessageEntity savedMessage = tgMrMessageService.getById(messageId);
        TgMrMessageEntity sentMessage = sendMessageToTg(savedMessage);
        tgMrMessageService.save(sentMessage);
    }

    private TgMrMessageEntity sendMessageToTg(TgMrMessageEntity entity) {
        if (entity.isDraft()) {
            log.debug("Mr with id: {} has draft status", entity.getMrId());
            return entity;
        }

        List<MessageChatsEntity> messageChats = entity.getChats()
                .stream()
                .toList();

        for (MessageChatsEntity messageChat : messageChats) {
            sender.sendMessage(entity.getText(), messageChat.getChatId())
                    .ifPresent(sent -> messageChat.setTgId(sent.getMessageId()));
        }

        return entity;
    }
}
