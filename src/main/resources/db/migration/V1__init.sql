CREATE TABLE battery (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    postcode VARCHAR(10) NOT NULL,
    capacity INTEGER NOT NULL
);
