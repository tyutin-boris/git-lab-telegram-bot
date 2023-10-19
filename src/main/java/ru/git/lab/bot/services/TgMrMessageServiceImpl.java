package ru.git.lab.bot.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.dto.MessageToDelete;
import ru.git.lab.bot.model.entities.TgMrMessageEntity;
import ru.git.lab.bot.model.repository.TgMrMessageRepository;
import ru.git.lab.bot.services.api.TgMrMessageService;
import ru.git.lab.bot.services.senders.api.MessageSender;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TgMrMessageServiceImpl implements TgMrMessageService {

    private final MessageSender messageSender;

    private final TgMrMessageRepository tgMrMessageRepository;

    @Override
    public Long saveMessage(Long chatId, String text, MergeRequestDto mergeRequest) {
        TgMrMessageEntity tgMrMessageEntity = createMessage(chatId, text, mergeRequest);
        TgMrMessageEntity savedEntity = tgMrMessageRepository.save(tgMrMessageEntity);
        log.debug("Save message with tgId: {}, authorId {} ", tgMrMessageEntity.getTgId(), tgMrMessageEntity.getAuthorId());
        return savedEntity.getId();
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
                .orElseThrow(() -> new RuntimeException("Message not found. mrId: " + mrId + ", " + "authorId: " + authorId));
    }

    @Override
    public Optional<TgMrMessageEntity> findByMrIdAndAuthorId(Long mrId, Long authorId) {
        return tgMrMessageRepository.findByMrIdAndAuthorId(mrId, authorId);
    }

    @Override
    public Optional<TgMrMessageEntity> findTgMrMessageById(Long id) {
        return tgMrMessageRepository.findById(id);
    }

    @Override
    public void save(TgMrMessageEntity entity) {
        tgMrMessageRepository.save(entity);
    }

    private TgMrMessageEntity createMessage(Long chatId, String text, MergeRequestDto mergeRequest) {
        TgMrMessageEntity tgMrMessageEntity = new TgMrMessageEntity();

        tgMrMessageEntity.setChatId(chatId);
        tgMrMessageEntity.setMrId(mergeRequest.getMrId());
        tgMrMessageEntity.setAuthorId(mergeRequest.getAuthor().getId());
        tgMrMessageEntity.setText(text);
        tgMrMessageEntity.setDraft(mergeRequest.isDraft());
        tgMrMessageEntity.setCreateDateTime(OffsetDateTime.now());

        return tgMrMessageEntity;
    }
}
