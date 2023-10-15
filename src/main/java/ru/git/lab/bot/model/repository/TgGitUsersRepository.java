package ru.git.lab.bot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.git.lab.bot.model.entities.TgGitUserId;
import ru.git.lab.bot.model.entities.TgGitUsersEntity;

import java.util.List;

@Repository
public interface TgGitUsersRepository extends JpaRepository<TgGitUsersEntity, TgGitUserId> {
    List<TgGitUsersEntity> findByTgId(Long tgId);
}
