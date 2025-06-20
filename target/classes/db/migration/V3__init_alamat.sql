CREATE TABLE IF NOT EXISTS alamat (
    id SERIAL PRIMARY KEY,
    address VARCHAR(255)
);

INSERT INTO alamat (address) VALUES
('Jl. Merdeka No. 10, Jakarta'),
('Jl. Surabaya No. 15, Bandung'),
('Jl. Ahmad Yani No. 20, Surabaya');
