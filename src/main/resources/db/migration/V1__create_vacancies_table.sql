    CREATE TABLE vacancies (
        id SERIAL PRIMARY KEY,
        title VARCHAR(100) NOT NULL,
        description TEXT NOT NULL,
        salary NUMERIC NOT NULL
    );

