ALTER TABLE IF EXISTS tg_mr_messages
    DROP COLUMN IF EXISTS tg_id,
    DROP COLUMN IF EXISTS chat_id,
    ADD CONSTRAINT mr_id_uq UNIQUE (mr_id);
