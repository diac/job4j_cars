ALTER TABLE car ADD COLUMN body_style_id INTEGER REFERENCES body_style(id);
ALTER TABLE car ADD COLUMN exterior_color_id INTEGER REFERENCES exterior_color(id);
ALTER TABLE car ADD COLUMN transmission_type_id INTEGER REFERENCES transmission_type(id);
ALTER TABLE car ADD COLUMN drivetrain_id INTEGER REFERENCES drivetrain(id);
ALTER TABLE car ADD COLUMN steering_wheel_side VARCHAR;