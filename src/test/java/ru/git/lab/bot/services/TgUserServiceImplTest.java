package ru.git.lab.bot.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.telegram.telegrambots.meta.api.objects.User;
import ru.git.lab.bot.model.entities.TgUserEntity;
import ru.git.lab.bot.model.repository.TgUserRepository;
import ru.git.lab.bot.services.api.TgUserService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class TgUserServiceImplTest {

    @Autowired
    private TgUserService sut;

    @Autowired
    private TgUserRepository tgUserRepository;

    @Test
    public void shouldSaveTgUser() {
        //given
        User user = getUser();

        //when
        sut.save(user);

        //then
        TgUserEntity actual = tgUserRepository.findById(user.getId()).orElse(null);

        assertThat(actual).isNotNull();
        assertThat(actual.getFirstName()).isEqualTo(user.getFirstName());
        assertThat(actual.getLastName()).isEqualTo(user.getLastName());
        assertThat(actual.getUsername()).isEqualTo(user.getUserName());
    }

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
