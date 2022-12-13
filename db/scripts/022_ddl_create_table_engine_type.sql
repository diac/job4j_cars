CREATE TABLE engine_type(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

COMMENT ON TABLE engine_type IS 'Тип двигателя';