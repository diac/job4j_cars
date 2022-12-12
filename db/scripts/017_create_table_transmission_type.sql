CREATE TABLE transmission_type(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

COMMENT ON TABLE transmission_type IS 'Тип коробки передач';