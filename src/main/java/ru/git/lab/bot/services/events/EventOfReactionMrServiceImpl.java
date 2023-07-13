package ru.git.lab.bot.services.events;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.model.entities.ApproveEntity;
import ru.git.lab.bot.model.entities.MessageEntity;
import ru.git.lab.bot.services.ApproveService;
import ru.git.lab.bot.services.MessageService;
import ru.git.lab.bot.services.MrTextMessageService;
import ru.git.lab.bot.services.senders.MessageSender;

import java.util.List;

import static ru.git.lab.bot.utils.ObjectAttributesUtils.getObjectAttributes;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventOfReactionMrServiceImpl implements EventOfReactionMrService {

    private final MessageSender sender;
    private final MessageService messageService;
    private final ApproveService approveService;
    private final MrTextMessageService mrTextMessageService;

    @Override
    public void addReactionToMessage(MergeRequestEvent event) {
        ObjectAttributes objectAttributes = getObjectAttributes(event);
        Long mrId = objectAttributes.getId();
        Long authorId = objectAttributes.getAuthorId();

        List<ApproveEntity> approvalsForMr = approveService.findAllByMrId(mrId);
        String text = mrTextMessageService.createMergeRequestTextMessageWithApprove(event, approvalsForMr);

        MessageEntity messageEntity = messageService.getMessageByMrIdAndAuthorId(mrId, authorId);
        sender.updateMessage(text, messageEntity.getChatId(), messageEntity.getMessageId());
    }
}
