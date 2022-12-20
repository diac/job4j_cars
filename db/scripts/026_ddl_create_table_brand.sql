CREATE TABLE brand (
    id SERIAL PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE
);

COMMENT ON TABLE brand IS 'Марка';
COMMENT ON COLUMN brand.id IS 'Идентификатор марки';
COMMENT ON COLUMN brand.name IS 'Имя марки';