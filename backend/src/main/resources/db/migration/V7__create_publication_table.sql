CREATE TABLE publication (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    title VARCHAR(255) NOT NULL,

    publisher VARCHAR(150) NOT NULL,

    publication_date DATE NOT NULL,

    publication_url VARCHAR(500),

    description TEXT,

    display_order INT NOT NULL,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP NOT NULL
);