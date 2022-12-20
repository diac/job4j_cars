CREATE TABLE driver (
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    user_id INTEGER UNIQUE NOT NULL REFERENCES auto_user(id)
);

COMMENT ON TABLE driver IS 'Водители';
COMMENT ON COLUMN driver.id IS 'Идентификатор водителя';
COMMENT ON COLUMN driver.name IS 'Имя водителя';
COMMENT ON COLUMN driver.user_id IS 'Идентификатор пользователя (FK)';