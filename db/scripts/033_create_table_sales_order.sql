CREATE TABLE sales_order(
    id SERIAL PRIMARY KEY,
    post_id INTEGER NOT NULL UNIQUE REFERENCES auto_post(id),
    previous_owner_user_id INTEGER NOT NULL REFERENCES auto_user(id),
    new_owner_user_id INTEGER NOT NULL REFERENCES auto_user(id),
    created TIMESTAMP
)