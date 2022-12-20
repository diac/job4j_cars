CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    name varchar,
    engine_id INTEGER REFERENCES engine(id)
);

COMMENT ON TABLE car IS 'Автомобили';
COMMENT ON COLUMN car.id IS 'Идентификатор автомобиля';
COMMENT ON COLUMN car.name IS 'Имя автомобиля';
COMMENT ON COLUMN car.engine_id IS 'Идентификатор двигателя (FK)';