package ru.git.lab.bot.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.dto.MessageToDelete;
import ru.git.lab.bot.model.entities.MessageChatsEntity;
import ru.git.lab.bot.model.entities.TgMrMessageEntity;
import ru.git.lab.bot.model.repository.TgMrMessageRepository;
import ru.git.lab.bot.services.api.TgMrMessageService;
import ru.git.lab.bot.services.senders.api.MessageSender;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TgMrMessageServiceImpl implements TgMrMessageService {

    private final MessageSender messageSender;

    private final TgMrMessageRepository tgMrMessageRepository;

    @Override
    public Long save(List<Long> chatIds, String text, MergeRequestDto mergeRequest) {
        TgMrMessageEntity tgMrMessageEntity = createMessage(chatIds, text, mergeRequest);
        TgMrMessageEntity savedEntity = tgMrMessageRepository.save(tgMrMessageEntity);
        Long id = savedEntity.getId();
        log.debug("Save tg message id. id: {}", id);
        return id;
    }

    @Transactional
    @Override
    public void delete(TgMrMessageEntity messages) {
        Set<MessageChatsEntity> chats = messages.getChats();

        for (MessageChatsEntity chat : chats) {
            Integer tgId = chat.getTgId();
            Long chatId = chat.getChatId();

            boolean messageWasDelete = messageSender.deleteMessage(chatId, tgId);

            if (messageWasDelete) {
                messages.setDelete(true);
                tgMrMessageRepository.save(messages);
                log.debug("Message was delete. tgId " + tgId + ", chatId " + chatId);
            }
        }
    }

    @Override
    public TgMrMessageEntity getMessageByMrIdAndAuthorId(Long mrId, Long authorId) {
        return tgMrMessageRepository.findByMrIdAndAuthorId(mrId, authorId)
                .orElseThrow(() -> new RuntimeException("Message not found. mrId: " + mrId + ", " + "authorId: " + authorId));
    }

    @Override
    public void save(TgMrMessageEntity entity) {
        tgMrMessageRepository.save(entity);
    }

    @Override
    public void updateText(String text, TgMrMessageEntity entity) {
        entity.setText(text);
        tgMrMessageRepository.save(entity);
    }

    @Override
    public Optional<TgMrMessageEntity> findByMrId(Long mrId) {
        return tgMrMessageRepository.findByMrId(mrId);
    }

    @Override
    public TgMrMessageEntity getById(Long id) {
        return tgMrMessageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TgMrMessages not found. id " + id));
    }

    private TgMrMessageEntity createMessage(List<Long> chatIds, String text, MergeRequestDto mergeRequest) {
        Long mrId = mergeRequest.getMrId();
        Long authorId = mergeRequest.getAuthor()
                .getId();

        Set<MessageChatsEntity> chats = chatIds.stream()
                .map(chatId -> createMessageChats(mrId, chatId))
                .collect(Collectors.toSet());

        TgMrMessageEntity tgMrMessageEntity = new TgMrMessageEntity();
        tgMrMessageEntity.setMrId(mrId);
        tgMrMessageEntity.setAuthorId(authorId);
        tgMrMessageEntity.setText(text);
        tgMrMessageEntity.setDraft(mergeRequest.isDraft());
        tgMrMessageEntity.setChats(chats);
        tgMrMessageEntity.setCreateDateTime(OffsetDateTime.now());

        return tgMrMessageEntity;
    }

    private MessageChatsEntity createMessageChats(Long tgMrMessageId, Long chatId) {
        MessageChatsEntity entity = new MessageChatsEntity();
        entity.setTgMrMessagesId(tgMrMessageId);
        entity.setChatId(chatId);
        return entity;
    }
}
