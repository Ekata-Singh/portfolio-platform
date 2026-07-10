CREATE TABLE education (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    degree VARCHAR(255) NOT NULL,

    institution VARCHAR(255) NOT NULL,

    field_of_study VARCHAR(255),

    start_year INT NOT NULL,

    end_year INT,

    grade VARCHAR(100),

    description TEXT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);