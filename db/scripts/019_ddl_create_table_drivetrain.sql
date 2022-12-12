CREATE TABLE drivetrain(
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

COMMENT ON TABLE drivetrain IS 'Тип привода';