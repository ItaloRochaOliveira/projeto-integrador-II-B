CREATE DATABASE IF NOT EXISTS user_db;

USE user_db;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at DATE NOT NULL DEFAULT (CURRENT_DATE),
    update_at DATE NOT NULL DEFAULT (CURRENT_DATE),
    deleted_at DATE NOT NULL DEFAULT (CURRENT_DATE),

);

#User 1: Senha 12345678
#User 2: Senha 123456789
INSERT INTO users (id, name, email, password) 
VALUES (1, 'user1', 'email1@gmail.com', '$2a$14$3VQd1eVpV93I4xytpPbWjeoGCPvBeVyzKt1YZpotVBHA38AyU7p/m'), (2, 'user2', 'email2@gmail.com', '$2a$14$3D7Dk5F0H0FtVVW3MrLHU.2tunRFn7bYiFIQ4zLF1VJ4DcSz5mYBW');