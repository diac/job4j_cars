CREATE TABLE history_owner (
    id SERIAL PRIMARY KEY,
    car_id INTEGER NOT NULL REFERENCES car(id),
    driver_id INTEGER NOT NULL REFERENCES driver(id),
    startAt DATE NOT NULL,
    endAt DATE,
    UNIQUE(car_id, driver_id, startAt, endAt)
)