CREATE TABLE IF NOT EXISTS chats_tg_git_users
(
    id      BIGINT NOT NULL PRIMARY KEY,
    tg_id   BIGINT NOT NULL,
    git_id  BIGINT,
    chat_id BIGINT NOT NULL
);

ALTER TABLE chats_tg_git_users
    ADD CONSTRAINT tg_id_git_id_uk UNIQUE (tg_id, git_id);
