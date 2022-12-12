CREATE TABLE exterior_color(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

COMMENT ON TABLE exterior_color IS 'Цвет';