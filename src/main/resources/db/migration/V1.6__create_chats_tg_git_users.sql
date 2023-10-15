CREATE TABLE IF NOT EXISTS chats_tg_git_users
(
    tg_id   BIGINT NOT NULL,
    git_id  BIGINT NOT NULL,
    chat_id BIGINT NOT NULL,
    PRIMARY KEY (tg_id, git_id, chat_id)
);
