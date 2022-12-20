CREATE TABLE engine_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

COMMENT ON TABLE engine_type IS 'Тип двигателя';
COMMENT ON COLUMN engine_type.id IS 'Идентификатор типа двигателя';
COMMENT ON COLUMN engine_type.name IS 'Имя типа двигателя';