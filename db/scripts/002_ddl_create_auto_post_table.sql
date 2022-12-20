CREATE TABLE auto_post (
    id SERIAL PRIMARY KEY,
    description TEXT,
    created TIMESTAMP,
    auto_user_id INT REFERENCES auto_user(id)
);

COMMENT ON TABLE auto_post IS 'Объявления';
COMMENT ON COLUMN auto_post.id IS 'Идентификатор объявления';
COMMENT ON COLUMN auto_post.description IS 'Текстовое описание объявления';
COMMENT ON COLUMN auto_post.created IS 'Дата и время создания объявления';
COMMENT ON COLUMN auto_post.auto_user_id IS 'Идентификатор пользователя, разместившего объявление (FK)';