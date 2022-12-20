CREATE TABLE participates(
    user_id INT,
    post_id INT,
    PRIMARY KEY(user_id, post_id)
);

COMMENT ON TABLE participates IS 'Подписки на объявления';
COMMENT ON COLUMN participates.user_id IS 'Идентификатор пользователя (FK)';
COMMENT ON COLUMN participates.post_id IS 'Идентификатор объявления (FK)';