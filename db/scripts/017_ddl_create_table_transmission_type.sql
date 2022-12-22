CREATE TABLE transmission_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

COMMENT ON TABLE transmission_type IS 'Тип коробки передач';
COMMENT ON COLUMN transmission_type.id IS 'Идентификатор типа коробки передач';
COMMENT ON COLUMN transmission_type.name IS 'Имя типа коробки передач';