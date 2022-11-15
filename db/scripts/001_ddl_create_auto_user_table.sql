CREATE TABLE auto_user (
  id SERIAL PRIMARY KEY,
  login VARCHAR NOT NULL UNIQUE,
  password VARCHAR NOT NULL
);

COMMENT ON TABLE auto_user IS 'Пользователи сайта';