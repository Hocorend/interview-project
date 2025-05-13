CREATE TABLE person (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL CHECK (LENGTH(name) > 3 AND LENGTH(name) < 15),
                        age INT CHECK ( age >= 18 ),
                        street VARCHAR(255),
                        building_number INT NOT NULL
);

CREATE TABLE brothers (
                          id SERIAL PRIMARY KEY,
                          person_id INT NOT NULL REFERENCES person(id) ON DELETE CASCADE,
                          name VARCHAR(255) NOT NULL,
                          age INT NOT NULL
);