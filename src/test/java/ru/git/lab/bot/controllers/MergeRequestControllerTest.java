package ru.git.lab.bot.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import ru.git.lab.bot.api.mr.*;
import ru.git.lab.bot.builders.MergeRequestEventTestBuilder;
import ru.git.lab.bot.model.entities.ApproveEntity;
import ru.git.lab.bot.model.entities.ChatsTgGitUsersEntity;
import ru.git.lab.bot.model.entities.GitUserEntity;
import ru.git.lab.bot.model.entities.MessageEntity;
import ru.git.lab.bot.model.repository.*;
import ru.git.lab.bot.services.mr.handlers.*;
import ru.git.lab.bot.services.senders.api.MessageSender;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
    private GitUserRepository gitUserRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ApproveRepository approveRepository;

    @Autowired
    private ChatsTgGitUsersRepository chatsTgGitUsersRepository;

    @SpyBean
    private MrOpenEventHandler mrOpenEventHandler;

    @SpyBean
    private MrReopenEventHandler mrReopenEventHandler;

    @SpyBean
    private MrCloseEventHandler mrCloseEventHandler;

    @SpyBean
    private MrMergeEventHandler mrMergeEventHandler;

    @SpyBean
    private MrApprovedEventHandler mrApprovedEventHandler;

    @SpyBean
    private MrUnapprovedEventHandler mrUnapprovedEventHandler;

    @SpyBean
    private MrUpdateEventHandler mrUpdateEventHandler;

    @SpyBean
    private MessageSender messageSender;

//    @BeforeEach
//    public void setUp() {
//        createChat();
//    }

//    @Test
//    public void shouldSendMessageWhenOpenMr() {
//        given
//        User expectedUser = createExpectedUser();
//        MergeRequestEvent open = createOpenMergeRequestEvent(expectedUser);
//        saveMrChat(expectedUser);
//
//        when
//        sut.mergeRequestEvent(open);
//
//        then
//        verify(mrOpenEventHandler).handleEvent(any());
//        verify(messageSender).sendMessage(any(), eq(chatId));
//
//        checkUserSave(expectedUser);
//        checkMessageSave(mrId, expectedUser.getId());
//    }

//    @Test
//    public void shouldDeleteMessageWhenCloseMr() {
//        //given
//        User expectedUser = createExpectedUser();
//
//        MergeRequestEvent open = createOpenMergeRequestEvent(expectedUser);
//        sut.mergeRequestEvent(open);
//
//        MergeRequestEvent close = createCloseMergeRequestEvent(expectedUser);
//
//        //when
//        sut.mergeRequestEvent(close);
//
//        //then
//        verify(mrCloseEventHandler).handleEvent(any());
//        verify(messageSender).deleteMessage(eq(chatId), any());
//
//        Optional<MessageEntity> actualMessage = messageRepository.findByMrIdAndAuthorId(mrId, expectedUser.getId());
//        assertThat(actualMessage.isEmpty()).isTrue();
//    }
//
//    @Test
//    public void shouldSendMessageWhenReopenMr() {
//        //given
//        User expectedUser = createExpectedUser();
//
//        MergeRequestEvent open = createOpenMergeRequestEvent(expectedUser);
//        sut.mergeRequestEvent(open);
//
//        MergeRequestEvent reopen = createReopenMergeRequestEvent(expectedUser);
//
//        //when
//        sut.mergeRequestEvent(reopen);
//
//        //then
//        verify(mrReopenEventHandler).handleEvent(any());
////        verify(messageSender, times(2)).sendMessage(any(), eq(chatId));
//
//        checkUserSave(expectedUser);
//        checkMessageSave(mrId, expectedUser.getId());
//    }
//
//    @Test
//    public void shouldDeleteMessageWhenMergeMr() {
//        //given
//        String openMessage = getMessage("mr/open.json");
//        sut.mergeRequestEvent(openMessage);
//
//        String closeMessage = getMessage("mr/merge.json");
//
//        //when
//        sut.mergeRequestEvent(closeMessage);
//
//        //then
//        verify(mrMergeEventHandler).handleEvent(any());
//        verify(messageSender).deleteMessage(eq(chatId), any());
//
//        Optional<MessageEntity> actualMessage = messageRepository.findByMrIdAndAuthorId(mrId, authorId);
//        assertThat(actualMessage.isEmpty()).isTrue();
//    }
//
//    @Test
//    public void shouldNotSendMessageWhenMrHasDraftStatus() {
////        //given
////        User expectedUser = createExpectedUser();
////        String message = getMessage("mr/open_draft.json");
////
////        //when
////        sut.mergeRequestEvent(message);
////
////        //then
////        verify(mrOpenEventHandler).handleEvent(any());
////        verify(messageSender, never()).sendMessage(any(), eq(chatId));
////
////        checkUserSave(expectedUser);
////        MessageEntity messageEntity = messageRepository.findByMrIdAndAuthorId(mrId, authorId)
////                .orElse(null);
////
////        assertThat(messageEntity).isNull();
//    }
//
//    @Test
//    public void shouldSendApproveWhenApprovedMr() {
//        //given
//        String openMessage = getMessage("mr/open.json");
//        sut.mergeRequestEvent(openMessage);
//
//        String approvedMessage = getMessage("mr/approved.json");
//
//        //when
//        sut.mergeRequestEvent(approvedMessage);
//
//        //then
//        verify(mrApprovedEventHandler).handleEvent(any());
//        verify(messageSender).updateMessage(any(), eq(chatId), any());
//
//        checkApproveSave();
//    }
//
//    @Test
//    public void shouldSendUnapprovedWhenUnapprovedMr() {
//        //given
//        String openMessage = getMessage("mr/open.json");
//        sut.mergeRequestEvent(openMessage);
//
//        String approvedMessage = getMessage("mr/approved.json");
//        sut.mergeRequestEvent(approvedMessage);
//
//        String unapprovedMessage = getMessage("mr/unapproved.json");
//
//        //when
//        sut.mergeRequestEvent(unapprovedMessage);
//
//        //then
//        verify(mrUnapprovedEventHandler).handleEvent(any());
//        verify(messageSender, times(2)).updateMessage(any(), eq(chatId), any());
//
//        checkApproveDelete();
//    }
//
//    @Test
//    public void shouldSendApproveWhenUpdateMr() {
//        //given
//        String openMessage = getMessage("mr/open.json");
//        sut.mergeRequestEvent(openMessage);
//
//        String approvedMessage = getMessage("mr/update.json");
//
//        //when
//        sut.mergeRequestEvent(approvedMessage);
//
//        //then
//        verify(mrUpdateEventHandler).handleEvent(any());
//        verify(messageSender).updateMessage(any(), eq(chatId), any());
//    }

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

    private void checkMessageSave(long mrId, long authorId) {
        MessageEntity messageEntity = messageRepository.findByMrIdAndAuthorId(mrId, authorId)
                .orElse(null);

        assertThat(messageEntity).isNotNull();
        assertThat(messageEntity.getChatId()).isEqualTo(chatId);

    }

    private void checkUserSave(User expected) {
        GitUserEntity actual = gitUserRepository.findById(expected.getId())
                .orElse(null);

        assertThat(actual).isNotNull();
        assertThat(actual.getName()).isEqualTo(expected.getName());
        assertThat(actual.getUsername()).isEqualTo(expected.getUsername());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
    }

//    private void createChat() {
//        ChatEntity chatEntity = new ChatEntity();
//        chatEntity.setId(chatId);
//        chatEntity.setType(ChatType.CHANNEL);
//        chatEntity.setTitle("test chat");
//
//        chatRepository.save(chatEntity);
//    }

    public String getMessage(String filePath) {
        try {
            URI uri = ClassLoader.getSystemResource(filePath)
                    .toURI();
            return Files.readString(Paths.get(uri));
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Failed to read file " + filePath, e);
        }
    }

    private User createExpectedUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Ivan");
        user.setUsername("ivanFirst");
        user.setEmail("ivan@mail.ru");
        return user;
    }

    private MergeRequestEvent createOpenMergeRequestEvent(User user) {
        long userId = user.getId();

        ObjectAttributes objectAttributes = new ObjectAttributes();
        objectAttributes.setAction(Action.OPEN);
        objectAttributes.setId(mrId);
        objectAttributes.setAuthorId(userId);
        objectAttributes.setDetailedMergeStatus(DetailedMergeStatus.MERGEABLE.getName());

        objectAttributes.setTitle("Title");
        objectAttributes.setDescription("Description");
        objectAttributes.setAuthorId(userId);
        objectAttributes.setSourceBranch("dev");
        objectAttributes.setTargetBranch("master");
        objectAttributes.setUrl("url");

        Project project = new Project();
        project.setName("First project");

        Reviewer reviewer = new Reviewer();
        reviewer.setName("ReviewerName");

        return new MergeRequestEventTestBuilder().objectAttributes(objectAttributes)
                .user(user)
                .project(project)
                .reviewers(List.of(reviewer))
                .build();
    }

    private MergeRequestEvent createCloseMergeRequestEvent(User user) {
        MergeRequestEvent open = createOpenMergeRequestEvent(user);

        ObjectAttributes openMrAttributes = open.getObjectAttributes();
        openMrAttributes.setAction(Action.CLOSE);

        return open;
    }

    private MergeRequestEvent createReopenMergeRequestEvent(User user) {
        MergeRequestEvent open = createOpenMergeRequestEvent(user);

        ObjectAttributes openMrAttributes = open.getObjectAttributes();
        openMrAttributes.setAction(Action.REOPEN);

        return open;
    }

    private void saveMrChat(User expectedUser) {
        ChatsTgGitUsersEntity chatsTgGitUsersEntity = new ChatsTgGitUsersEntity();
        chatsTgGitUsersEntity.setChatId(chatId);
        chatsTgGitUsersEntity.setGitId(expectedUser.getId());
        chatsTgGitUsersEntity.setTgId(12345L);

        chatsTgGitUsersRepository.save(chatsTgGitUsersEntity);
    }
}
