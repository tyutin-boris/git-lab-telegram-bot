package ru.git.lab.bot.mappers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.git.lab.bot.api.mr.Action;
import ru.git.lab.bot.api.mr.DetailedMergeStatus;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.dto.AuthorDto;
import ru.git.lab.bot.dto.MergeRequestDto;
import ru.git.lab.bot.dto.UserDto;

import static org.assertj.core.api.Assertions.*;

@SpringJUnitConfig(classes = MergeRequestMapperImpl.class)
class MergeRequestMapperTest {

    @Autowired
    private MergeRequestMapper sut;

    @Test
    public void shouldMapToDto() {
        //given
        MergeRequestEvent event = getMergeRequestEvent();
        long mrId = event.getObjectAttributes().getId();

        //when
        MergeRequestDto actual = sut.toDto(event);

        //then
        assertThat(actual.getMrId()).isEqualTo(mrId);
        assertThat(actual.getUser().getId()).isEqualTo();
        assertThat(actual.getUser().getName()).isEqualTo();
        assertThat(actual.getUser().getUsername()).isEqualTo();
        assertThat(actual.getUser().getEmail()).isEqualTo();
        assertThat(actual.getAuthor().getId()).isEqualTo();
        assertThat(actual.getAction()).isEqualTo();
        assertThat(actual.getDetailedMergeStatus()).isEqualTo();
        assertThat(actual.getProjectName()).isEqualTo();
        assertThat(actual.getTitle()).isEqualTo();
        assertThat(actual.getDescription()).isEqualTo();
        assertThat(actual.getReviewerName()).isEqualTo();
        assertThat(actual.getSourceBranch()).isEqualTo();
        assertThat(actual.getTargetBranch()).isEqualTo();
        assertThat(actual.getMrUrl()).isEqualTo();
    }

    private MergeRequestEvent getMergeRequestEvent() {
        ObjectAttributes objectAttributes = new ObjectAttributes();
        objectAttributes.setId(1L);

        MergeRequestEvent mergeRequestEvent = new MergeRequestEvent();
        mergeRequestEvent.setObjectAttributes(objectAttributes);
        return mergeRequestEvent;
    }
}
