CREATE TABLE profile (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    full_name VARCHAR(100) NOT NULL,

    headline VARCHAR(255),

    about TEXT,

    email VARCHAR(100) NOT NULL,

    phone VARCHAR(20),

    location VARCHAR(100),

    github_url VARCHAR(255),

    linkedin_url VARCHAR(255),

    resume_url VARCHAR(255),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP

);