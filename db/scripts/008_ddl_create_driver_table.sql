CREATE TABLE driver (
    id SERIAL PRIMARY KEY,
    name VARCHAR,
    user_id INTEGER UNIQUE NOT NULL REFERENCES auto_user(id)
);