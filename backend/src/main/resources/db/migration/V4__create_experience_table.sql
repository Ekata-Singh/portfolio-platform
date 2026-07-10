CREATE TABLE experience (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    company VARCHAR(255) NOT NULL,
    job_title VARCHAR(255) NOT NULL,
    employment_type VARCHAR(100),

    location VARCHAR(255),

    start_date DATE NOT NULL,
    end_date DATE,

    currently_working BOOLEAN NOT NULL DEFAULT FALSE,

    description TEXT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);