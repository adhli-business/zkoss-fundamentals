-- Tabel role
CREATE TABLE IF NOT EXISTS roles (
    id INT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

-- Tabel user
CREATE TABLE IF NOT EXISTS "users" (
    id INT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Tabel user_roles (many-to-many relasi antara user dan role)
CREATE TABLE IF NOT EXISTS user_roles (
    user_id INT,
    role_id INT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

-- V1__Insert_initial_data.sql --
INSERT INTO roles (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO users (id, username, password) VALUES (1, 'admin', '$2a$10$oRm5ddTjHMhNsi.NVKRLQOiUXz6qs1zPUhkZGd5TM/7Y0F/MLkBae');
INSERT INTO users (id, username, password) VALUES (2, 'user', '$2a$10$4koVDNa1I8P6u2LqN4fx5e1hSgdkabthueqrWpTJGi9GFIIDUD4AC');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 1);