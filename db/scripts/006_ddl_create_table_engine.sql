CREATE TABLE engine (
    id SERIAL PRIMARY KEY,
    name VARCHAR
);

COMMENT ON TABLE engine IS 'Двигатели';
COMMENT ON COLUMN engine.id IS 'Идентификатор двигателя';
COMMENT ON COLUMN engine.name IS 'Имя двигателя';