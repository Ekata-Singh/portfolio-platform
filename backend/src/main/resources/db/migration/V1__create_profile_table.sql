CREATE TABLE profile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    full_name VARCHAR(255) NOT NULL,

    headline VARCHAR(255),

    about TEXT,

    email VARCHAR(255) NOT NULL,

    phone VARCHAR(20),

    location VARCHAR(255),

    github_url VARCHAR(255),

    linkedin_url VARCHAR(255),

    profile_image_url VARCHAR(500),

    resume_url VARCHAR(500),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);