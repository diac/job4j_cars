CREATE TABLE body_style(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

COMMENT ON TABLE body_style IS 'Тип кузова';