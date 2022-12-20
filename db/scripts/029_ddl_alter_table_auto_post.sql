ALTER TABLE auto_post ADD COLUMN available BOOLEAN NOT NULL DEFAULT true;
ALTER TABLE auto_post ADD COLUMN price INTEGER NOT NULL;

COMMENT ON COLUMN auto_post.available IS 'Флаг доступности объявления (true/false)';
COMMENT ON COLUMN auto_post.price IS 'Цена, указанная в объявлении';