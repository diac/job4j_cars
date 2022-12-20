ALTER TABLE car DROP COLUMN IF EXISTS engine_volume_id;
ALTER TABLE car ADD COLUMN engine_volume INTEGER NOT NULL DEFAULT 0;

COMMENT ON COLUMN car.engine_volume IS 'Объем двигателя в миллилитрах';