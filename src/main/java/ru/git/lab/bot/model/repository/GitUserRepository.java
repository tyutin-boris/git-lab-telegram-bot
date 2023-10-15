package ru.git.lab.bot.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.git.lab.bot.model.entities.GitUserEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GitUserRepository extends JpaRepository<GitUserEntity, Long> {

    boolean existsByIdAndUsernameAndEmail(Long gitId, String username, String email);
}
