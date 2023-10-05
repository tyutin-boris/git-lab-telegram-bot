package ru.git.lab.bot.mappers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.DetailedMergeStatus;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.api.mr.Project;
import ru.git.lab.bot.api.mr.Reviewer;
import ru.git.lab.bot.api.mr.User;
import ru.git.lab.bot.dto.AuthorDto;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.dto.UserDto;
import ru.git.lab.bot.model.entities.GitUserEntity;
import ru.git.lab.bot.services.api.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitConfig(classes = MergeRequestMapperImpl.class)
class MergeRequestMapperTest {

    @Autowired
    private MergeRequestMapper sut;

    @MockBean
    private UserService userService;

    @Test
    public void shouldMapToDto() {
        //given
        MergeRequestDto expected = getMergeRequestDto();
        MergeRequestEvent event = getMergeRequestEvent();
        mockUserService();

        //when
        MergeRequestDto actual = sut.toDto(event);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private void mockUserService() {
        GitUserEntity user = new GitUserEntity();
        user.setName("name");

        when(userService.getByAuthorId(any())).thenReturn(user);
    }

    private MergeRequestDto getMergeRequestDto() {
        UserDto user = new UserDto();
        user.setId(1L);
        user.setName("name");
        user.setUsername("username");
        user.setEmail("user@mail.ru");

        AuthorDto author = new AuthorDto();
        author.setId(1L);

        MergeRequestDto dto = new MergeRequestDto();
        dto.setMrId(1L);
        dto.setUser(user);
        dto.setAuthor(author);
        dto.setDetailedMergeStatus(DetailedMergeStatus.MERGEABLE);
        dto.setProjectName("projectName");
        dto.setTitle("title");
        dto.setDescription("description");
        dto.setReviewerName("reviewerName");
        dto.setSourceBranch("source");
        dto.setTargetBranch("target");
        dto.setMrUrl("mrUrl");
        dto.setAction(Action.OPEN);

        return dto;
    }

    private MergeRequestEvent getMergeRequestEvent() {
        ObjectAttributes objectAttributes = new ObjectAttributes();
        objectAttributes.setId(1L);
        objectAttributes.setAuthorId(1L);
        objectAttributes.setTitle("title");
        objectAttributes.setDescription("description");
        objectAttributes.setSourceBranch("source");
        objectAttributes.setTargetBranch("target");
        objectAttributes.setDetailedMergeStatus(DetailedMergeStatus.MERGEABLE.getName());
        objectAttributes.setUrl("mrUrl");
        objectAttributes.setAction(Action.OPEN);

        Project project = new Project();
        project.setName("projectName");

        User user = new User();
        user.setId(1L);
        user.setName("name");
        user.setUsername("username");
        user.setEmail("user@mail.ru");

        Reviewer reviewer = new Reviewer();
        reviewer.setName("reviewerName");

        MergeRequestEvent mergeRequestEvent = new MergeRequestEvent();
        mergeRequestEvent.setObjectAttributes(objectAttributes);
        mergeRequestEvent.setProject(project);
        mergeRequestEvent.setUser(user);
        mergeRequestEvent.setReviewers(List.of(reviewer));
        return mergeRequestEvent;
    }
}
