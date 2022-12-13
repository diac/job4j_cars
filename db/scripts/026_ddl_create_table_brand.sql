CREATE TABLE brand(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

COMMENT ON TABLE engine_volume IS 'Марка';