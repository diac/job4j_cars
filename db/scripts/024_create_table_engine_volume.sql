CREATE TABLE engine_volume(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

COMMENT ON TABLE engine_volume IS 'Объем двигателя';