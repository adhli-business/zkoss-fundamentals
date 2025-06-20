-- Create table for Role
CREATE TABLE IF NOT EXISTS role (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)
);

-- Create table for User (pakai quote)
CREATE TABLE IF NOT EXISTS "user" (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255),
    password VARCHAR(255)
);

-- Create join table for User and Role (many-to-many)
CREATE TABLE IF NOT EXISTS user_role (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user"(id),
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role(id)
);

-- Insert default roles (tanpa id, biar auto increment)
INSERT INTO role (name) VALUES ('ROLE_USER');
INSERT INTO role (name) VALUES ('ROLE_ADMIN');

ALTER TABLE "user" ADD COLUMN IF NOT EXISTS password VARCHAR(255);

-- Insert default users (tanpa id, biar auto increment)
INSERT INTO "user" (username, password) VALUES 
('admin', '$2a$10$oRm5ddTjHMhNsi.NVKRLQOiUXz6qs1zPUhkZGd5TM/7Y0F/MLkBae'),
('user', '$2a$10$4koVDNa1I8P6u2LqN4fx5e1hSgdkabthueqrWpTJGi9GFIIDUD4AC');

-- Assign roles to users (asumsi id admin=1, user=2, role_user=1, role_admin=2)
INSERT INTO user_role (user_id, role_id) VALUES 
(1, 1), -- admin gets ROLE_USER
(1, 2), -- admin also gets ROLE_ADMIN
(2, 1); -- user gets ROLE_USER