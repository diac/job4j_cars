CREATE TABLE drivetrain(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

COMMENT ON TABLE drivetrain IS 'Тип привода';
COMMENT ON COLUMN drivetrain.id IS 'Идентификатор типа привода';
COMMENT ON COLUMN drivetrain.name IS 'Имя типа привода';