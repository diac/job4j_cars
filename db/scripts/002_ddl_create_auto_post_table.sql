CREATE TABLE auto_post(
    id SERIAL PRIMARY KEY,
    "text" TEXT,
    created TIMESTAMP,
    auto_user_id INT REFERENCES auto_users(id)
);