CREATE TABLE exterior_color(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

COMMENT ON TABLE exterior_color IS 'Цвет';
COMMENT ON COLUMN exterior_color.id IS 'Идентификатор цвета';
COMMENT ON COLUMN exterior_color.name IS 'Имя цвета';