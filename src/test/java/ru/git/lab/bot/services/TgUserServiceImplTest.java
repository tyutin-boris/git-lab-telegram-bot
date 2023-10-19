package ru.git.lab.bot.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.git.lab.bot.model.entities.TgUserEntity;
import ru.git.lab.bot.model.repository.TgUserRepository;
import ru.git.lab.bot.services.api.TgUserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class TgUserServiceImplTest {

    @Autowired
    private TgUserService sut;

    @SpyBean
    private TgUserRepository tgUserRepository;

//    @Test
//    public void shouldSaveTgUser() {
//        //given
//        User user = getUser();
//
//        //when
//        sut.save(user);
//
//        //then
//        TgUserEntity actual = tgUserRepository.findById(user.getId()).orElse(null);
//
//        assertThat(actual).isNotNull();
//        assertThat(actual.getFirstName()).isEqualTo(user.getFirstName());
//        assertThat(actual.getLastName()).isEqualTo(user.getLastName());
//        assertThat(actual.getUsername()).isEqualTo(user.getUserName());
//    }
//
//    @Test
//    public void shouldNotSaveTgUserWhenItExist() {
//        //given
//        User user = getUser();
//        sut.save(user);
//
//        //when
//        sut.saveUserIfNotExist(user);
//
//        //then
//        verify(tgUserRepository, atLeastOnce()).save(any());
//    }
//
//
//    @Test
//    public void shouldThrowExceptionWhenSaveTgUserIsNull() {
//        //given
//        String expected = "User no save because is is null";
//
//        //when
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> sut.save(null));
//
//        //then
//        String actual = exception.getMessage();
//        assertThat(actual).isEqualTo(expected);
//    }
//
//
//    @Test
//    public void shouldThrowExceptionWhenSaveIfNotExistTgUserIsNull() {
//        //given
//        String expected = "User no save because is is null";
//
//        //when
//        RuntimeException exception = assertThrows(RuntimeException.class, () -> sut.saveUserIfNotExist(null));
//
//        //then
//        String actual = exception.getMessage();
//        assertThat(actual).isEqualTo(expected);
//    }

    private User getUser() {
        User from = new User();
        from.setId(586815794L);
        from.setFirstName("Boris");
        from.setIsBot(false);
        from.setLastName("Tyutin");
        from.setUserName("btyutin");
        return from;
    }
}
