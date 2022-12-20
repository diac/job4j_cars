CREATE TABLE history_owner (
    car_id INTEGER NOT NULL REFERENCES car(id),
    driver_id INTEGER NOT NULL REFERENCES driver(id),
    startAt DATE NOT NULL,
    endAt DATE,
    PRIMARY KEY(car_id, driver_id, startAt, endAt)
);

COMMENT ON TABLE history_owner IS 'История владения автомобилем';
COMMENT ON COLUMN history_owner.car_id IS 'Идентификатор автомобиля (FK)';
COMMENT ON COLUMN history_owner.driver_id IS 'Идентификатор водителя (владельца) (FK)';
COMMENT ON COLUMN history_owner.startAt IS 'Дата начала владения';
COMMENT ON COLUMN history_owner.endAt IS 'Дата окончания владения';