CREATE TABLE price_history (
   id SERIAL PRIMARY KEY,
   before BIGINT NOT NULL,
   after BIGINT NOT NULL,
   created TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
   post_id int NOT NULL REFERENCES auto_post(id)
);

COMMENT ON TABLE price_history IS 'История изменения цен в объявлении';
COMMENT ON COLUMN price_history.id IS 'Идентификатор записи об изменении цены';
COMMENT ON COLUMN price_history.before IS 'Цена до изменения';
COMMENT ON COLUMN price_history.after IS 'Новая цена';
COMMENT ON COLUMN price_history.created IS 'Дата и время изменения цены';
COMMENT ON COLUMN price_history.post_id IS 'Идентификатор объявления (FK)';