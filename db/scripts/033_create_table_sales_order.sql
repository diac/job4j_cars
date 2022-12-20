CREATE TABLE sales_order (
    id SERIAL PRIMARY KEY,
    post_id INTEGER NOT NULL UNIQUE REFERENCES auto_post(id),
    previous_owner_user_id INTEGER NOT NULL REFERENCES auto_user(id),
    new_owner_user_id INTEGER NOT NULL REFERENCES auto_user(id),
    created TIMESTAMP
);

COMMENT ON TABLE sales_order IS 'Записи о совершении сделок';
COMMENT ON COLUMN sales_order.id IS 'Идентификатор записи о совершении сделки';
COMMENT ON COLUMN sales_order.post_id IS 'Идентификатор объявления (FK)';
COMMENT ON COLUMN sales_order.previous_owner_user_id IS 'Идентификатор пользователя-продавца (FK)';
COMMENT ON COLUMN sales_order.new_owner_user_id IS 'Идентификатор пользователя-покупателя (FK)';
COMMENT ON COLUMN sales_order.created IS 'Дата и время совершения сделки';