CREATE TABLE IF NOT EXISTS chats_git_users
(
    id          BIGINT NO NULL PRIMARY KEY,
    chat_id     BIGINT    NOT NULL REFERENCES chats (id),
    git_user_id BIGINT    NOT NULL REFERENCES git_users (id)
);
