package ru.git.lab.bot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.git.lab.bot.model.entities.GitUserEntity;
import ru.git.lab.bot.model.entities.TgGitUsersEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface TgGitUsersRepository extends JpaRepository<TgGitUsersEntity, Long> {
    Optional<TgGitUsersEntity> findByGitUsername(String gitUsername);
}
