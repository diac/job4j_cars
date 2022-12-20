ALTER TABLE car DROP COLUMN name;
ALTER TABLE car ADD COLUMN model_name VARCHAR NOT NULL;
ALTER TABLE car DROP COLUMN engine_id;
ALTER TABLE car ADD COLUMN engine_type_id INTEGER NOT NULL REFERENCES engine_type(id);
ALTER TABLE car ADD COLUMN engine_volume_id INTEGER NOT NULL REFERENCES engine_volume(id);
ALTER TABLE car ADD COLUMN brand_id INTEGER NOT NULL REFERENCES brand(id);
ALTER TABLE car ADD COLUMN horsepower INTEGER NOT NULL;
ALTER TABLE car ADD COLUMN production_year INTEGER NOT NULL;

COMMENT ON COLUMN car.model_name IS 'Модель автомобиля';
COMMENT ON COLUMN car.engine_type_id IS 'Идентификатор типа двигателя (FK)';
COMMENT ON COLUMN car.engine_volume_id IS 'Идентификатор объема двигателя (FK)';
COMMENT ON COLUMN car.brand_id IS 'Идентификатор марки (FK)';
COMMENT ON COLUMN car.horsepower IS 'Мощность двигателя в лошадиных силах';
COMMENT ON COLUMN car.production_year IS 'Год выпуска автомобиля';