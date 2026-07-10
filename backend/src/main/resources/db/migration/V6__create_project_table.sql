CREATE TABLE project (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    project_name VARCHAR(255) NOT NULL,

    description TEXT,

    technologies VARCHAR(500),

    project_url VARCHAR(500),

    github_url VARCHAR(500),

    display_order INT NOT NULL,

    created_at TIMESTAMP,

    updated_at TIMESTAMP

);