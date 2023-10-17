CREATE TABLE If NOT EXISTS tg_git_user
(
    tg_id  BIGINT NOT NULL PRIMARY KEY,
    git_id BIGINT,
    git_username TEXT NOT NULL UNIQUE
);
