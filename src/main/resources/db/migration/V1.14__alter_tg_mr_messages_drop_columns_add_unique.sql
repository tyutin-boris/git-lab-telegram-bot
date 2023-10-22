ALTER TABLE tg_mr_messages
    DROP COLUMN tg_id;

ALTER TABLE tg_mr_messages
    DROP COLUMN chat_id;

ALTER TABLE tg_mr_messages
    ADD CONSTRAINT mr_id_uq UNIQUE (mr_id);
