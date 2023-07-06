package org.boris.bot.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.boris.bot.api.Action;
import org.boris.bot.api.MergeRequest;
import org.boris.bot.api.ObjectAttributes;
import org.boris.bot.model.repository.ChatRepository;
import org.boris.bot.services.senders.MergeRequestSender;
import org.boris.bot.services.MergeRequestService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Optional;

import static org.boris.bot.utils.MergeRequestUtils.createMergeRequestMessage;
import static org.boris.bot.api.Action.INDEFINITELY;

@Slf4j
@Service
@RequiredArgsConstructor
public class MergeRequestServiceImpl implements MergeRequestService {

    private final MergeRequestSender sender;
    private final ChatRepository chatRepository;
//    private final MessageRepository messageRepository;

    @Override
    public void sendMergeRequestMessage(MergeRequest request) {
        ObjectAttributes objectAttributes = getObjectAttributes(request);
        Long mrId = objectAttributes.getId();
        Action action = getAction(objectAttributes);

        switch (action) {
            case OPEN:
            case REOPEN:
                log.debug("Merge request action OPEN. MR id: " + mrId);
                sendMergeRequestOpenOrReopen(request);
                break;
            case CLOSE:
                log.warn("Merge request action CLOSE, message about MR with id " + mrId + "should delete");
                break;
            case UPDATE:
                log.debug("Merge request action UPDATE. MR id: " + mrId);
                break;
            case APPROVED:
                log.debug("Merge request action APPROVED. MR id: " + mrId);
                break;
            case UNAPPROVED:
                log.debug("Merge request action UNAPPROVED. MR id: " + mrId);
                break;
            case APPROVAL:
                log.debug("Merge request action APPROVAL. MR id: " + mrId);
                break;
            case UNAPPROVAL:
                log.debug("Merge request action UNAPPROVAL. MR id: " + mrId);
                break;
            case MERGE:
                log.warn("Merge request action MERGE, message about MR with id " + mrId + "should delete");
                break;
            default:
                log.warn("Merge request action not defined");
                break;
        }
    }

    private void sendMergeRequestOpenOrReopen(MergeRequest request) {
        String text = createMergeRequestMessage(request);

        List<Long> chatsId = chatRepository.getAllId();

        for (Long id : chatsId) {
            Message message = sender.sendMessage(text, id);
            System.out.println();
        }
    }

    private void sendMergeRequestClose(MergeRequest request) {
//        List<Long> chatsId = getChatsId();

//        for (Long id : chatsId) {
//            sender.deleteMessage(id, );
//            System.out.println();
//        }
    }

    private Action getAction(ObjectAttributes objectAttributes) {
        return Optional.ofNullable(objectAttributes.getAction())
                .orElse(INDEFINITELY);
    }

    private ObjectAttributes getObjectAttributes(MergeRequest request) {
        return Optional.of(request)
                .map(MergeRequest::getObjectAttributes)
                .orElseThrow(() -> new RuntimeException("Merge request without object attributes"));
    }
}
