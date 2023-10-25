package ru.git.lab.bot.services.api;

import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.model.entities.TgMrMessageEntity;

import java.util.List;
import java.util.Optional;

public interface TgMrMessageService {

    Long save(List<Long> chatIds, String text, MergeRequestDto mergeRequest);

    void delete(TgMrMessageEntity messages);

    TgMrMessageEntity getMessageByMrIdAndAuthorId(Long mrId, Long authorId);

    void save(TgMrMessageEntity entity);

    void updateText(String text, TgMrMessageEntity entity);

    Optional<TgMrMessageEntity> findByMrId(Long mrId);

    TgMrMessageEntity getById(Long id);

    TgMrMessageEntity getByMrId(Long mrId);
}
