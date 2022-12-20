CREATE TABLE engine_volume(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

COMMENT ON TABLE engine_volume IS 'Объем двигателя';
COMMENT ON COLUMN engine_volume.id IS 'Идентификатор объема двигателя';
COMMENT ON COLUMN engine_volume.name IS 'Имя объема двигателя';