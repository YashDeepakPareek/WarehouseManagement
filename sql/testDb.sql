CREATE DATABASE warehouse_db;
CREATE USER 'warehouse_admin'@'localhost' IDENTIFIED BY 'admin123';
use sys;
GRANT ALL PRIVILEGES ON warehouse_db.* TO 'warehouse_admin'@'localhost';
FLUSH PRIVILEGES;

use warehouse_db;
CREATE TABLE IF NOT EXISTS login (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

INSERT INTO login (username, password) VALUES ('admin', 'admin123');