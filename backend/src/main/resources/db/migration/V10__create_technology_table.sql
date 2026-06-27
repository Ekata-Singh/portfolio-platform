CREATE TABLE technology (

    id BIGINT PRIMARY KEY AUTO_INCREMENT,

    technology_name VARCHAR(255) NOT NULL,

    category VARCHAR(100) NOT NULL,

    icon_url VARCHAR(500),

    proficiency VARCHAR(50),

    display_order INT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP
);