package ru.git.lab.bot.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import ru.git.lab.bot.dto.ChatType;
import ru.git.lab.bot.model.entities.ApproveEntity;
import ru.git.lab.bot.model.entities.ChatEntity;
import ru.git.lab.bot.model.entities.GitUserEntity;
import ru.git.lab.bot.model.entities.MessageEntity;
import ru.git.lab.bot.model.repository.ApproveRepository;
import ru.git.lab.bot.model.repository.ChatRepository;
import ru.git.lab.bot.model.repository.MessageRepository;
import ru.git.lab.bot.model.repository.UserRepository;
import ru.git.lab.bot.services.mr.handlers.MrApprovedEventHandler;
import ru.git.lab.bot.services.mr.handlers.MrCloseEventHandler;
import ru.git.lab.bot.services.mr.handlers.MrMergeEventHandler;
import ru.git.lab.bot.services.mr.handlers.MrOpenEventHandler;
import ru.git.lab.bot.services.mr.handlers.MrUnapprovedEventHandler;
import ru.git.lab.bot.services.senders.api.MessageSender;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class MergeRequestControllerTest {

    private final long chatId = -1001833741964L;

    private final long mrId = 235411851L;

    private final long authorId = 14826841L;

    @Autowired
    private MergeRequestController sut;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ApproveRepository approveRepository;

    @SpyBean
    private MrOpenEventHandler mrOpenEventHandler;

    @SpyBean
    private MrCloseEventHandler mrCloseEventHandler;

    @SpyBean
    private MrMergeEventHandler mrMergeEventHandler;

    @SpyBean
    private MrApprovedEventHandler mrApprovedEventHandler;

    @SpyBean
    private MrUnapprovedEventHandler mrUnapprovedEventHandler;

    @SpyBean
    private MessageSender messageSender;

    @BeforeEach
    public void setUp() {
        createChat();
    }

    @Test
    public void shouldSendMessageToTgWhenCreteNewMR() {
        //given
        String message = getMessage("mr/open.json");

        //when
        sut.mergeRequestEvent(message);

        //then
        verify(mrOpenEventHandler).handleEvent(any());
//        verify(messageSender).sendMessage(any(), eq(chatId));

        checkUserSave();
        checkMessageSave();
    }

    @Test
    public void shouldDeleteMessageToTgWhenMRIsClose() {
        //given
        String openMessage = getMessage("mr/open.json");
        sut.mergeRequestEvent(openMessage);

        String closeMessage = getMessage("mr/close.json");

        //when
        sut.mergeRequestEvent(closeMessage);

        //then
        verify(mrCloseEventHandler).handleEvent(any());
        verify(messageSender).deleteMessage(eq(chatId), any());

        Optional<MessageEntity> actualMessage = messageRepository.findByMrIdAndAuthorId(mrId, authorId);
        assertThat(actualMessage.isEmpty()).isTrue();
    }

    @Test
    public void shouldDeleteMessageToTgWhenMRIsMerge() {
        //given
        String openMessage = getMessage("mr/open.json");
        sut.mergeRequestEvent(openMessage);

        String closeMessage = getMessage("mr/merge.json");

        //when
        sut.mergeRequestEvent(closeMessage);

        //then
        verify(mrMergeEventHandler).handleEvent(any());
        verify(messageSender).deleteMessage(eq(chatId), any());

        Optional<MessageEntity> actualMessage = messageRepository.findByMrIdAndAuthorId(mrId, authorId);
        assertThat(actualMessage.isEmpty()).isTrue();
    }

    @Test
    public void shouldNotSendMessageToTgWhenCreteNewMRWithDraftStatus() {
        //given
        String message = getMessage("mr/open_draft.json");

        //when
        sut.mergeRequestEvent(message);

        //then
        verify(mrOpenEventHandler).handleEvent(any());
        verify(messageSender, never()).sendMessage(any(), eq(chatId));

        checkUserSave();
        Long mrId = 414770L;
        Long authorId = 14826841L;

        MessageEntity messageEntity = messageRepository.findByMrIdAndAuthorId(mrId, authorId)
                .orElse(null);

        assertThat(messageEntity).isNull();
    }

    @Test
    public void shouldSendApproveToMessage() {
        //given
        String openMessage = getMessage("mr/open.json");
        sut.mergeRequestEvent(openMessage);

        String approvedMessage = getMessage("mr/approved.json");

        //when
        sut.mergeRequestEvent(approvedMessage);

        //then
        verify(mrApprovedEventHandler).handleEvent(any());
        verify(messageSender).updateMessage(any(), eq(chatId), any());

        checkApproveSave();
    }

    @Test
    public void shouldSendUnapprovedToMessage() {
        //given
        String openMessage = getMessage("mr/open.json");
        sut.mergeRequestEvent(openMessage);

        String approvedMessage = getMessage("mr/approved.json");
        sut.mergeRequestEvent(approvedMessage);

        String unapprovedMessage = getMessage("mr/unapproved.json");

        //when
        sut.mergeRequestEvent(unapprovedMessage);

        //then
        verify(mrUnapprovedEventHandler).handleEvent(any());
        verify(messageSender, times(2)).updateMessage(any(), eq(chatId), any());

        checkApproveDelete();
    }

    private void checkApproveDelete() {
        List<ApproveEntity> approves = approveRepository.findAllByMrIdAndAuthorId(mrId, authorId);
        assertThat(approves).isEmpty();
    }

    private void checkApproveSave() {
        List<ApproveEntity> approves = approveRepository.findAllByMrIdAndAuthorId(mrId, authorId);
        ApproveEntity actualApprove = approves.stream()
                .findFirst()
                .orElse(null);
        assertThat(actualApprove).isNotNull();
        assertThat(actualApprove.getAuthorName()).isEqualTo("Ivan Ivanov");
    }

    private void checkMessageSave() {
        MessageEntity messageEntity = messageRepository.findByMrIdAndAuthorId(mrId, authorId)
                .orElse(null);

        assertThat(messageEntity).isNotNull();
        assertThat(messageEntity.getChatId()).isEqualTo(chatId);

    }

    private void checkUserSave() {
        GitUserEntity userEntity = userRepository.findByGitId(14826841L)
                .orElse(null);

        assertThat(userEntity).isNotNull();
        assertThat(userEntity.getName()).isEqualTo("Ivan Ivanov");
        assertThat(userEntity.getUsername()).isEqualTo("ivan");
        assertThat(userEntity.getEmail()).isEqualTo("ivan@mail.ru");
    }

    private void createChat() {
        ChatEntity chatEntity = new ChatEntity();
        chatEntity.setId(chatId);
        chatEntity.setType(ChatType.CHANNEL);
        chatEntity.setTitle("test chat");
        chatEntity.setCreateDate(OffsetDateTime.now());

        chatRepository.save(chatEntity);
    }

    public String getMessage(String filePath) {
        try {
            URI uri = ClassLoader.getSystemResource(filePath)
                    .toURI();
            return Files.readString(Paths.get(uri));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Failed to read file " + filePath, e);
        }
    }
}
