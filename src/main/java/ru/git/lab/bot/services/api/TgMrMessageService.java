package ru.git.lab.bot.services.api;

import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.dto.MessageToDelete;
import ru.git.lab.bot.model.entities.TgMrMessageEntity;

import java.util.List;
import java.util.Optional;

public interface TgMrMessageService {

    Long saveMessage(Long chatId, String text, MergeRequestDto mergeRequest);

    List<MessageToDelete> getMessageToDelete(Long mrId, Long authorId);

    void deleteMessages(List<MessageToDelete> messages);

    TgMrMessageEntity getMessageByMrIdAndAuthorId(Long mrId, Long authorId);

    Optional<TgMrMessageEntity> findByMrIdAndAuthorId(Long mrId, Long authorId);

    Optional<TgMrMessageEntity> findTgMrMessageById(Long id);

    void save(TgMrMessageEntity entity);

    void updateTgIdAndDraftStatus(Integer tgid, TgMrMessageEntity entity);

    void updateText(String text, TgMrMessageEntity entity);
}
