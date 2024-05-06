CREATE TABLE IF NOT EXISTS users (
                                     id INT AUTO_INCREMENT  PRIMARY KEY,
                                     email VARCHAR(250) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100)
    );