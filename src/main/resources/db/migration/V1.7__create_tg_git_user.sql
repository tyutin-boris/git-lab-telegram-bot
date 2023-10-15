CREATE TABLE If NOT EXISTS tg_git_user
(
    tg_id  BIGINT NOT NULL,
    git_id BIGINT NOT NULL,

    PRIMARY KEY (tg_id, git_id)
);
