CREATE TABLE auto_user (
  id SERIAL PRIMARY KEY,
  login VARCHAR NOT NULL UNIQUE,
  password VARCHAR NOT NULL
);

COMMENT ON TABLE auto_user IS 'Пользователи сайта';
COMMENT ON COLUMN auto_user.id IS 'Идентификатор пользователя';
COMMENT ON COLUMN auto_user.login IS 'Логин пользователя в системе';
COMMENT ON COLUMN auto_user.password IS 'Пароль пользователя в системе';