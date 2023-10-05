package ru.git.lab.bot.services.mr;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.model.entities.ApproveEntity;
import ru.git.lab.bot.model.entities.MessageEntity;
import ru.git.lab.bot.services.api.ApproveService;
import ru.git.lab.bot.services.api.MessageService;
import ru.git.lab.bot.services.api.MrTextMessageService;
import ru.git.lab.bot.services.mr.api.ReactionMrService;
import ru.git.lab.bot.services.senders.api.MessageSender;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactionMrServiceImpl implements ReactionMrService {

    private final MessageSender sender;
    private final MessageService messageService;
    private final ApproveService approveService;
    private final MrTextMessageService mrTextMessageService;

    @Override
    public void addReactionToMessage(MergeRequestDto mergeRequest) {
        long mrId = mergeRequest.getMrId();
        long authorId = mergeRequest.getAuthor().getId();

        List<ApproveEntity> approvalsForMr = approveService.findAllByMrId(mrId);
        String text = mrTextMessageService.createMergeRequestTextMessageWithApprove(mergeRequest, approvalsForMr);

        MessageEntity messageEntity = messageService.getMessageByMrIdAndAuthorId(mrId, authorId);
        sender.updateMessage(text, messageEntity.getChatId(), messageEntity.getMessageId());
    }
}
