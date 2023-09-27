package ru.git.lab.bot.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import ru.git.lab.bot.dto.ChatType;
import ru.git.lab.bot.model.entities.ChatEntity;
import ru.git.lab.bot.model.entities.GitUserEntity;
import ru.git.lab.bot.model.entities.MessageEntity;
import ru.git.lab.bot.model.repository.ChatRepository;
import ru.git.lab.bot.model.repository.MessageRepository;
import ru.git.lab.bot.model.repository.UserRepository;
import ru.git.lab.bot.services.mr.handlers.MrApprovedEventHandler;
import ru.git.lab.bot.services.mr.handlers.MrOpenEventHandler;
import ru.git.lab.bot.services.senders.api.MessageSender;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class MergeRequestControllerTest {

    private final long chatId = -1001833741964L;

    @Autowired
    private MergeRequestController mergeRequestController;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @SpyBean
    private MrOpenEventHandler mrOpenEventHandler;

    @SpyBean
    private MrApprovedEventHandler mrApprovedEventHandler;

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
        mergeRequestController.mergeRequestEvent(message);

        //then
        verify(mrOpenEventHandler).handleEvent(any());
        verify(messageSender).sendMessage(any(), eq(chatId));

        checkUserSave();
        checkMessageSave();
    }

    @Test
    public void shouldNotSendMessageToTgWhenCreteNewMRWithDraftStatus() {
        //given
        String message = getMessage("mr/open_draft.json");

        //when
        mergeRequestController.mergeRequestEvent(message);

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
        mergeRequestController.mergeRequestEvent(openMessage);

        String approvedMessage = getMessage("mr/approved.json");

        //when
        mergeRequestController.mergeRequestEvent(approvedMessage);

        //then
        verify(mrApprovedEventHandler).handleEvent(any());
        verify(messageSender).sendMessage(any(), eq(chatId));

        checkUserSave();
        checkMessageSave();
    }

    private void checkMessageSave() {
        Long mrId = 235411851L;
        Long authorId = 14826841L;
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

