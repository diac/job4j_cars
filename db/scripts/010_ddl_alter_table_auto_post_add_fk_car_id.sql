ALTER TABLE auto_post ADD COLUMN car_id INTEGER REFERENCES car(id);

COMMENT ON COLUMN auto_post.car_id IS 'Идентификатор автомобиля (FK)';