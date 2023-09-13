package ru.git.lab.bot.services.mr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.DetailedMergeStatus;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.model.entities.MessageEntity;
import ru.git.lab.bot.services.api.ChatService;
import ru.git.lab.bot.services.api.MessageService;
import ru.git.lab.bot.services.api.MrTextMessageService;
import ru.git.lab.bot.services.mr.api.CreateMrService;
import ru.git.lab.bot.services.senders.api.MessageSender;

import java.util.List;
import java.util.Optional;

import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateMrServiceImpl implements CreateMrService {

    private final MessageSender sender;

    private final ChatService chatService;

    private final MessageService messageService;

    private final MrTextMessageService mrTextMessageService;

    @Override
    public void sendAndSaveMessage(MergeRequestEvent event) {
        ObjectAttributes objectAttributes = getObjectAttributes(event);
        Long mrId = objectAttributes.getId();
        Long authorId = objectAttributes.getAuthorId();
        DetailedMergeStatus detailedMergeStatus = DetailedMergeStatus.getStatus(objectAttributes.getDetailedMergeStatus());

        List<Long> chatsId = chatService.getAllChatId();
        String text = mrTextMessageService.createMergeRequestTextMessage(event);

        for (Long id : chatsId) {
            Optional<MessageEntity> messageEntity = messageService.findByMrIdAndAuthorId(mrId, authorId);

            messageEntity.ifPresent(e -> {
                log.debug("Message for mr with id " + mrId + " and authorId " + authorId + " already sent");
            });

            if (messageEntity.isEmpty() && !DetailedMergeStatus.DRAFT_STATUS.equals(detailedMergeStatus)) {
                sender.sendMessage(text, id)
                        .ifPresent(m -> messageService.saveMessage(m, objectAttributes));
            }
        }

        chatLog(chatsId);
    }

    private void chatLog(List<Long> chatsId) {
        if(chatsId.isEmpty()) {
            log.debug("Chat list is empty. Messages not send");
        }
    }
}
