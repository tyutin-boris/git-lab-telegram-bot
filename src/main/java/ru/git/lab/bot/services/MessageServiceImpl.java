package ru.git.lab.bot.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.dto.MessageToDelete;
import ru.git.lab.bot.model.entities.TgMrMessageEntity;
import ru.git.lab.bot.model.repository.TgMrMessageRepository;
import ru.git.lab.bot.services.api.MessageService;
import ru.git.lab.bot.services.senders.api.MessageSender;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageSender messageSender;
    private final TgMrMessageRepository tgMrMessageRepository;

    @Override
    public void saveMessage(Message message, MergeRequestDto mergeRequest) {
        TgMrMessageEntity tgMrMessageEntity = createMessage(message, mergeRequest);
        tgMrMessageRepository.save(tgMrMessageEntity);
        log.debug("Save message with tgId " + tgMrMessageEntity.getTgId() + ", authorId " + tgMrMessageEntity.getAuthorId());
    }

    @Override
    public List<MessageToDelete> getMessageToDelete(Long mrId, Long authorId) {
        return tgMrMessageRepository.getMessageToDelete(mrId, authorId);
    }

    @Override
    @Transactional
    public void deleteMessages(List<MessageToDelete> messages) {
        for (MessageToDelete message : messages) {
            Integer tgId = message.getTgId();
            Long chatId = message.getChatId();

            boolean messageWasDelete = messageSender.deleteMessage(chatId, tgId);

            if (messageWasDelete) {
                tgMrMessageRepository.deleteByTgId(tgId);
                log.debug("Message was delete. tgId " + tgId + ", chatId " + chatId);
            }
        }
    }

    @Override
    public TgMrMessageEntity getMessageByMrIdAndAuthorId(Long mrId, Long authorId) {
        return tgMrMessageRepository.findByMrIdAndAuthorId(mrId, authorId)
                .orElseThrow(() -> new RuntimeException(
                        "Message not found. mrId: " + mrId + ", " + "authorId: " + authorId));
    }

    @Override
    public Optional<TgMrMessageEntity> findByMrIdAndAuthorId(Long mrId, Long authorId) {
        return tgMrMessageRepository.findByMrIdAndAuthorId(mrId, authorId);
    }

    private TgMrMessageEntity createMessage(Message message, MergeRequestDto mergeRequest) {
        TgMrMessageEntity tgMrMessageEntity = new TgMrMessageEntity();

        tgMrMessageEntity.setTgId(message.getMessageId());
        tgMrMessageEntity.setChatId(message.getChatId());
        tgMrMessageEntity.setMrId(mergeRequest.getMrId());
        tgMrMessageEntity.setAuthorId(mergeRequest.getAuthor().getId());
        tgMrMessageEntity.setCreateDateTime(OffsetDateTime.now());

        return tgMrMessageEntity;
    }
}
