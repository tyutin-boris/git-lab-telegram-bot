CREATE TABLE If NOT EXISTS tg_git_user
(
    id           BIGINT NOT NULL PRIMARY KEY,
    tg_id        BIGINT NOT NULL,
    git_id       BIGINT,
    git_username TEXT   NOT NULL UNIQUE
);
