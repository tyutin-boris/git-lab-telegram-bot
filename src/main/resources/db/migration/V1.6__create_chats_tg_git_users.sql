CREATE TABLE IF NOT EXISTS chats_tg_git_users
(
    tg_id   BIGINT NOT NULL PRIMARY KEY,
    git_id  BIGINT,
    chat_id BIGINT NOT NULL
);
