CREATE TABLE body_style(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

COMMENT ON TABLE body_style IS 'Тип кузова';
COMMENT ON COLUMN body_style.id IS 'Идентификатор типа кузова';
COMMENT ON COLUMN body_style.name IS 'Имя типа кузова';