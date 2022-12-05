CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    name varchar,
    engine_id INTEGER REFERENCES engine(id)
);