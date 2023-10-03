package ru.git.lab.bot.services.mr.handlers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.git.lab.bot.api.mr.MergeRequestEvent;
import ru.git.lab.bot.api.mr.ObjectAttributes;
import ru.git.lab.bot.services.mr.api.CloseMrService;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MrCloseEventHandlerTest {

    @Mock
    private CloseMrService closeMrService;

    @InjectMocks
    private MrCloseEventHandler sut;

    @Test
    public void shouldCallDeleteMessageMethod_whenMrIsClose() {
        //given
        long mrId = 1L;
        long authorId = 2L;
        MergeRequestEvent event = createMrRequest(mrId, authorId);

        //when
//        sut.handleEvent(event);

        //then
        verify(closeMrService).deleteMessage(mrId, authorId);
    }

    private MergeRequestEvent createMrRequest(long mrId, long authorId) {
        MergeRequestEvent event = new MergeRequestEvent();
        ObjectAttributes objectAttributes = new ObjectAttributes();

        objectAttributes.setId(mrId);
        objectAttributes.setAuthorId(authorId);

        event.setObjectAttributes(objectAttributes);

        return event;
    }
}
