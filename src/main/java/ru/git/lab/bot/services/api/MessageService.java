package ru.git.lab.bot.services.api;

import org.telegram.telegrambots.meta.api.objects.Message;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.dto.MessageToDelete;
import ru.git.lab.bot.model.entities.TgMrMessageEntity;

import java.util.List;
import java.util.Optional;

public interface MessageService {

    void saveMessage(Message message, MergeRequestDto mergeRequest);

    List<MessageToDelete> getMessageToDelete(Long mrId, Long authorId);

    void deleteMessages(List<MessageToDelete> messages);

    TgMrMessageEntity getMessageByMrIdAndAuthorId(Long mrId, Long authorId);

    Optional<TgMrMessageEntity> findByMrIdAndAuthorId(Long mrId, Long authorId);
}
