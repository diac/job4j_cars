ALTER TABLE car ADD COLUMN body_style_id INTEGER REFERENCES body_style(id);
ALTER TABLE car ADD COLUMN exterior_color_id INTEGER REFERENCES exterior_color(id);
ALTER TABLE car ADD COLUMN transmission_type_id INTEGER REFERENCES transmission_type(id);
ALTER TABLE car ADD COLUMN drivetrain_id INTEGER REFERENCES drivetrain(id);
ALTER TABLE car ADD COLUMN steering_wheel_side VARCHAR;

COMMENT ON COLUMN car.body_style_id IS 'Идентификатор типа кузова (FK)';
COMMENT ON COLUMN car.exterior_color_id IS 'Идентификатор цвета (FK)';
COMMENT ON COLUMN car.exterior_color_id IS 'Идентификатор типа коробки передач (FK)';
COMMENT ON COLUMN car.drivetrain_id IS 'Идентификатор типа привода (FK)';
COMMENT ON COLUMN car.steering_wheel_side IS 'Руль (левый или правый)';