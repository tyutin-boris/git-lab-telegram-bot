CREATE TABLE IF NOT EXISTS private_chat_messages (
    id BIGINT NOT NULL PRIMARY KEY,
    tg_user_id BIGINT NOT NULL,
    chat_id BIGINT NOT NULL,
    text TEXT,
    bot_command TEXT NOT NULL,
    stage_stap INTEGER NOT NULL,
    create_date timestamp NOT NULL
);